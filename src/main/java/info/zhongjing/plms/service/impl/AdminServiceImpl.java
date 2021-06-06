package info.zhongjing.plms.service.impl;

import info.zhongjing.plms.commonutils.VerificationCodeUtils;
import info.zhongjing.plms.commonutils.oss.OSSUtils;
import info.zhongjing.plms.entity.Admin;
import info.zhongjing.plms.mapper.AdminMapper;
import info.zhongjing.plms.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/4/1 1:47 下午 </p>
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Admin login(String username, String password) {
        // 根据用户名查询用户是否存在
        Admin admin = adminMapper.selectByUsername(username);
        // 如果存在，判断密码是否正确，如果不存在，直接失败
        if (admin != null) {
            return admin.getPassword().equals(password) ? admin : null;
        }
        return admin;
    }

    @Override
    public boolean regist(Admin admin) {
        // 打印日志
        log.info("注册信息：" + admin);
        // 查看用户名和邮箱是否与已有用户重复
        boolean flag = adminMapper.selectByUsernameOrEmail(admin.getUsername(), admin.getEmail()) > 0;
        // 如果没有重复，就注册，有重复直接失败
        if (!flag) {
            adminMapper.add(admin);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMessage(Admin admin) {
        return adminMapper.update(admin);
    }

    @Override
    public Admin uploadHeadPortrait(Admin admin, MultipartFile file) {
        // 文件为空直接返回false
        if (file.isEmpty()) {
            return null;
        }

        // 使用工具类上传图片到阿里云OSS服务器，并获取图片的公网链接
        String filePath = OSSUtils.upload(file, admin.getUsername());

        // 上传不成功直接返回null
        if (filePath == null) {
            return null;
        }

        // 更新数据库中admin信息
        admin.setHeadPortrait(filePath);
        boolean flag = adminMapper.update(admin);

        return flag ? admin : null;
    }

    @Override
    public boolean usernameAndEmailExist(String username, String email) {
        Integer count = adminMapper.selectByUsernameAndEmail(username, email);
        return count > 0;
    }

    @Override
    public boolean sendEmail(String username, String email) {
        try {
            // 如果redis中有验证码，说明这个人不是什么好人（刷新前端可以刷新js代码，不需要等60秒也能重新发送）
            // 拼接key（用户名+邮箱）
            String key = username + "_" + email;
            String value = redisTemplate.opsForValue().get(key);

            // redis中存在对应的验证码，直接返回false
            if (value != null) {
                return false;
            }

            SimpleMailMessage message = new SimpleMailMessage();

            // 发送人
            message.setFrom(from);

            // 接收人
            message.setTo("858149982@qq.com");

            // 标题
            message.setSubject("室内停车场管理系统");

            // 邮件内容
            // 固定内容
            String content = "你好，您本次的验证码为：";
            // 生成的验证码
            String code = VerificationCodeUtils.generateCode();
            // 设置邮件内容
            message.setText(content + code);
            // 记录日志
            log.info("验证码生成：" + code);

            // 记录验证码到redis
            redisTemplate.opsForValue().set(key, code, 60, TimeUnit.SECONDS);

            // 发送邮件
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean resetPassword(String username, String email, String code, String newPassword) {
        // 先判断用户名和邮箱存不存在
        Integer count = adminMapper.selectByUsernameAndEmail(username, email);
        // 如果存在才判断
        if (count > 0) {
            String redisCode = redisTemplate.opsForValue().get(username + "_" + email);
            if (code.equals(redisCode)) {
                // 根据用户名获取到用户的全部信息
                Admin admin = adminMapper.selectByUsername(username);
                // 更新用户的密码
                admin.setPassword(newPassword);
                // 持久化用户信息到数据库
                adminMapper.update(admin);
                return true;
            }
        }
        // 不存在直接返回false
        return false;
    }
}
