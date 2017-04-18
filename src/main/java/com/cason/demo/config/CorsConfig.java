package com.cason.demo.config;


import com.cason.demo.common.HeaderCons;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jingle.huang on 2017/4/18.
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders(HeaderCons.ACCESS_TOKEN,HeaderCons.MOBILE,HeaderCons.PARTNER_CODE)
                .maxAge(3600);
    }

}
