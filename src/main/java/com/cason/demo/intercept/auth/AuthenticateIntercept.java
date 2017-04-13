package com.cason.demo.intercept.auth;

import com.cason.demo.Service.AdminsService;
import com.cason.demo.common.HeaderCons;
import com.cason.demo.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录令牌权限校验拦截器
 */
public class AuthenticateIntercept extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(AuthenticateIntercept.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("AuthenticateIntercept----------校验，dosoming-----{}",request.getRequestURI());

        if(!request.getRequestURI().endsWith("login") && !request.getRequestURI().endsWith("error")){
            String accessToke = request.getHeader(HeaderCons.ACCESS_TOKEN);
            String mobile = request.getHeader(HeaderCons.MOBILE);
            if(!getAdminsService().checkToken(mobile,accessToke)){
                logger.info("无效的访问令牌 {}", accessToke);
                throw new RuntimeException("令牌失效！");
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {}

    public AdminsService getAdminsService() {
        return SpringContextUtil.getBean(AdminsService.class);
    }
}
