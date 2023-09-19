package com.hq.hqusercenter.service;


import com.hq.hqusercenter.HquserCenterApplication;
import com.hq.hqusercenter.mapper.UserMapper;
import com.hq.hqusercenter.model.domain.User;
import javafx.application.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/*
*用户服务测试
*@author hq
 */
@SpringBootTest(classes = {HquserCenterApplication.class})
//@MapperScan("com.hq.hqusercenter.mapper")
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user =new User();
        user.setUsername("DogYuPi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://i.imgur.com/ppaKWxc_d.jpg?maxwidth=520&shape=thumb&fidelity=high");
        user.setGender(0);
        user.setUserPassword("200113");
        user.setPhone("123");
        user.setEmail("456");
        user.setUpdateTime(new Date());
        user.setUserStatus(0);
        user.setIsDelete(0);
        user.setCreateTime(new Date());
        boolean result=userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount="yupi";
        String userPassword="";
        String checkPassword="123456";
        String planetCode="1";
        long result =userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yu";
        result=userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yupi";
        userPassword="123456";
        result=userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yu pi";
        userPassword="12345678";
        result=userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        checkPassword="123456789";
        result=userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="dogYupi";
        checkPassword="12345678";
        result=userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount="yupi";
        result=userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
    }
}