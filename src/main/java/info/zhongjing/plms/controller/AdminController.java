package info.zhongjing.plms.controller;

import info.zhongjing.plms.entity.Admin;
import info.zhongjing.plms.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author ZhongJing </p>
 * @Description 管理员Controller </p>
 * @date 2021/3/14 4:53 下午 </p>
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 去登陆页面
     */
    @GetMapping("/toLogin")
    public ModelAndView toLogin() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/login");

        return mav;
    }

    /**
     * 去注册页面
     */
    @GetMapping("/toRegist")
    public ModelAndView toRegist() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/regist");

        return mav;
    }

    /**
     * 去修改密码页面
     */
    @GetMapping("/toResetPassword")
    public ModelAndView toResetPassword() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/reset_password");

        return mav;
    }

    /**
     * 去个人信息页面
     */
    @GetMapping(value = "/toPersonalInformation")
    public ModelAndView toPersonalInformation() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/personal_information");

        return mav;
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @param session  用来保存用户信息到session域
     * @return true登陆成功，false登陆失败
     */
    @GetMapping(value = "/login")
    public boolean login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            log.info("用户信息已添加到session，" + admin);
            return true;
        }
        return false;
    }

    /**
     * 注册
     *
     * @param admin 用户信息
     */
    @PostMapping(value = "/regist")
    public boolean regist(Admin admin) {
        return adminService.regist(admin);
    }

    /**
     * 退出系统
     *
     * @param session 用于删除session域中的用户信息
     */
    @GetMapping(value = "/exit")
    public ModelAndView exit(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        session.removeAttribute("admin");

        mav.setViewName("redirect:/admin/toLogin");

        return mav;
    }

    /**
     * 修改个人信息
     *
     * @param admin   个人信息
     * @param session 用于保存更新后的个人信息
     * @return true成功，false失败
     */
    @PostMapping(value = "/updatePersonalInformation")
    public boolean updatePersonalInformation(@RequestBody Admin admin, HttpSession session) {
        // 调用service层方法修改个人信息
        boolean flag = adminService.updateMessage(admin);

        // 如果修改成功
        if (flag) {
            session.setAttribute("admin", admin);
            return true;
        }

        // 修改失败返回false
        return false;
    }

    /**
     * 修改头像
     *
     * @return true成功，false失败
     */
    @PostMapping(value = "/update_headPortrait")
    public boolean updateHeadPortrait(@RequestParam MultipartFile file, HttpSession session) {
        // 获取session域中用户的信息
        Admin admin = (Admin) session.getAttribute("admin");

        // 调用service层方法修改头像
        admin = adminService.uploadHeadPortrait(admin, file);

        // 如果修改成功
        if (admin != null) {
            // 用修改过的admin信息覆盖掉sesion中旧的admin信息
            session.setAttribute("admin", admin);
            // 打印日志
            log.info("头像修改成功，修改后的用户信息：" + admin);
            return true;
        }
        // 修改失败返回false
        return false;
    }

    /**
     * 判断用户名与对应邮箱是否存在
     *
     * @param username 用户名
     * @param email    邮箱
     * @return 存在返回true，不存在返回false
     */
    @GetMapping(value = "/selectByUsernameAndEmail")
    public boolean selectByUsernameAndEmail(@RequestParam String username, @RequestParam String email) {
        log.info("selectByUsernameAndEmail()：请求已进入");
        log.info("username:" + username);
        log.info("email:" + email);
        return adminService.usernameAndEmailExist(username, email);
    }

    /**
     * 发送验证码
     *
     * @param username 用户名
     * @param email    接收验证码的邮箱
     * @return true成功，fasle失败
     */
    @GetMapping(value = "/sendCode")
    public boolean sendCode(@RequestParam String username, @RequestParam String email) {
        return adminService.sendEmail(username, email);
    }

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param email       邮箱
     * @param code        验证码
     * @param newPassword 新密码
     * @return true成功，false失败
     */
    @PostMapping(value = "/resetPassword")
    public boolean resetPassword(@RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String code,
                                 @RequestParam("password") String newPassword) {
        log.info("用户名：" + username);
        log.info("邮箱：" + email);
        log.info("验证码：" + code);
        log.info("新密码：" + newPassword);
        return adminService.resetPassword(username, email, code, newPassword);
    }
}
