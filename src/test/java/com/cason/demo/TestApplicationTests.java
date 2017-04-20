package com.cason.demo;

import com.cason.demo.service.LyUserService;
import com.cason.demo.service.RedisService;
import com.cason.demo.model.LyUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {
	@Autowired
	LyUserService lyUserService;


	@Autowired
	RedisService redisService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUser(){
		LyUser user = lyUserService.selectByPrimaryKey(1);

		System.out.println(user);
	}

	@Test
	public void testUserPage(){
		System.out.println(lyUserService.selectAllBypage(1,1));

	}

	@Test
	public void testRedis(){
		redisService.set("testKey","testRedis90182012389123");
		System.out.println(redisService.get("testKey"));
	}

}
