package com.cason.demo.web.controller;

import com.cason.demo.config.SettingsRetriever;
import com.cason.demo.model.LyUser;
import com.cason.demo.service.LyUserService;
import com.cason.demo.web.ro.UserRo;
import com.cason.demo.web.vo.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jingle.huang on 2017/4/18.
 */
@RequestMapping("/user")
@RestController
@Api(value = "用户信息管理接口",tags = "用户信息管理接口")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private LyUserService lyUserService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private SettingsRetriever settingsRetriever;



    @ApiOperation(value="提交用户信息", notes="用于保存用户信息")
    @PostMapping(value = "/userAudit")
    public ResponseData userAudit(@RequestBody UserRo userRo) {
        log.info(userRo.toString());
        amqpTemplate.convertAndSend(settingsRetriever.getSenderRoutingKey(),userRo.toString());
        log.info("convertAndSend--------------------");
        return ResponseData.getSuccess();
    }

    @ApiOperation(value="获取用户信息", notes="根据用户id获取用户信息")
    @GetMapping(value = "/getUserInfo")
    public LyUser getUserInfo (Integer userId){
        LyUser fi = lyUserService.selectByPrimaryKey(userId);
        return fi;
    }
}
