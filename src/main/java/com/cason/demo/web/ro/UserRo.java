package com.cason.demo.web.ro;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jingle.huang on 2017/4/20.
 */
public class UserRo {

    @ApiModelProperty(value = "用户名称",required = true)
    private String name;

    @ApiModelProperty("登录密码")
    private String password;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("年龄")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserRo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", age=" + age +
                '}';
    }
}
