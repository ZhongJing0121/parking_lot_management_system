package info.zhongjing.plms.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/23 3:52 下午 </p>
 */
@Slf4j
@WebFilter(filterName = "UserFilter", urlPatterns = "/*")
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 获取当前请求
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String url = ((HttpServletRequest) servletRequest).getRequestURI();

        // 放行静态资源
        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png")
                || url.endsWith(".jpg") || url.endsWith("toLogin") || url.endsWith("toRegist")
                || url.endsWith("resetPassword") || url.endsWith(".jpeg") || url.endsWith("login")
                ||url.endsWith("regist") || url.endsWith("sendCode") || url.endsWith("toResetPassword")
                || url.endsWith("selectByUsernameAndEmail") || url.endsWith(".ico")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 获取session域中的admin
        Object admin = httpServletRequest.getSession().getAttribute("admin");

        // 判断session中的admin是否为空
        if (admin == null) {
            log.info("尝试未登陆访问系统功能失败……，重定向到登陆界面……" + "，访问路径：" + url);
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendRedirect("http://localhost/admin/toLogin");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
