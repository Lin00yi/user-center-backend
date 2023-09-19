package com.hq.hqusercenter;

import com.hq.hqusercenter.model.domain.User;
import com.hq.hqusercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@MapperScan("com.hq.hqusercenter.mapper")
class HquserCenterApplicationTests {



    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
    }


}
