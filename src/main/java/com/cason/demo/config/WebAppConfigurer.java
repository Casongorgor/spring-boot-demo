package com.cason.demo.config;

import com.cason.demo.common.HeaderCons;
import com.cason.demo.intercept.auth.AuthenticateIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(new AuthenticateIntercept()).addPathPatterns("/**");
        // registry.addInterceptor(new PagedInterceptor()).addPathPatterns("/**");
    }

    /**
     * 实现跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")//这里填写你允许进行跨域的主机ip
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")//允许的访问方法
                .allowedHeaders(HeaderCons.ACCESS_TOKEN,HeaderCons.MOBILE,HeaderCons.PARTNER_CODE)//允许Header的参数
                .maxAge(3600);//Access-Control-Max-Age 用于 CORS 相关配置的缓存
    }


}
