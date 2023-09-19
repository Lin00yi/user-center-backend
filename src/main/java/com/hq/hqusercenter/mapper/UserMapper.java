package com.hq.hqusercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hq.hqusercenter.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 林
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-09-12 20:52:03
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




