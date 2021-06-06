package info.zhongjing.plms.service;

import info.zhongjing.plms.entity.Admin;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/4/1 1:47 下午 </p>
 */
public interface AdminService {

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return 登陆成功返回用户信息，登陆失败返回null
     */
    Admin login(String username, String password);

    /**
     * 注册
     * @param admin 用户信息
     * @return true成功，false失败
     */
    boolean regist(Admin admin);

    /**
     * 更新个人信息
     * @param admin 新的个人信息
     * @return true成功，fasle失败
     */
    boolean updateMessage(Admin admin);

    /**
     * 保存头像文件
     * @param admin 用户信息
     * @param file 头像文件
     * @return 成功返回修改后的用户信息，失败返回null
     */
    Admin uploadHeadPortrait(Admin admin, MultipartFile file);

    /**
     * 用户名和对应邮箱是否存在
     * @param username 用户名
     * @param email 邮箱
     * @return 存在返回true，不存在返回false
     */
    boolean usernameAndEmailExist(String username, String email);

    /**
     * 以邮件的方式，发送验证码给指定邮箱
     * @param username 用户名
     * @param email 邮箱
     * @return true成功，false失败
     */
    boolean sendEmail(String username, String email);

    /**
     * 修改密码
     * @param username 用户名
     * @param email 邮箱
     * @param code 验证码
     * @param newPassword 新密码
     * @return true修改成功，false修改失败
     */
    boolean resetPassword(String username, String email, String code, String newPassword);
}
