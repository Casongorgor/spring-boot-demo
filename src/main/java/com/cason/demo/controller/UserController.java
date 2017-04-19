package com.cason.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jingle.huang on 2017/4/18.
 */
@RestController
public class UserController {

    @GetMapping(value = "/user/kuayu")
    public Object userAudit() {
        Map<String, String> result = new HashMap<>();
        result.put("key", "15112");
        result.put("mobile", "1515151515151");
        return result;
    }
}
