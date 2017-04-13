package com.cason.demo.controller;

import com.cason.demo.Service.LyUserService;
import com.cason.demo.config.SettingsRetriever;
import com.cason.demo.model.LyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created by jingle.huang on 2017/3/9.
 */
@Controller
public class IndexController {
    private final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    SettingsRetriever settingsRetriever;


    @Autowired
    LyUserService lyUserService;


    @RequestMapping("/")
    public String web(Map<String,Object> model){
        LyUser fi=lyUserService.selectByPrimaryKey(1);
        model.put("time",new Date());
        model.put("message",settingsRetriever.getMessage());
        model.put("name",fi.getAccountname());
        log.info("model = [" + model + "]");
        return "web";//返回的内容就是templetes下面文件的名称
    }



}
