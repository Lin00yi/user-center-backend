package com.hq.hqusercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hq.hqusercenter.common.BaseResponse;
import com.hq.hqusercenter.common.ErrorCode;
import com.hq.hqusercenter.common.ResultUtils;
import com.hq.hqusercenter.exception.BusinessException;
import com.hq.hqusercenter.model.domain.User;
import com.hq.hqusercenter.model.domain.request.UserLoginRequest;
import com.hq.hqusercenter.model.domain.request.UserRegisterRequest;
import com.hq.hqusercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.hq.hqusercenter.constant.UserConstant.ADMIN_ROLE;
import static com.hq.hqusercenter.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * 用户接口
 * @author hq
 */
@RestController

//@CrossOrigin(origins = "http://43.139.40.182")
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
        public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        String userAccount=userRegisterRequest.getUserAccount();
        String userPassword=userRegisterRequest.getUserPassword();
        String checkPassword=userRegisterRequest.getCheckPassword();
        String planetCode=userRegisterRequest.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        long result=userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        return ResultUtils.success(result);
    }
    @PostMapping("/add")
    public BaseResponse<Boolean> addUser(@RequestBody User user){
        if(user==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        String userAccount=user.getUserAccount();
        String userPassword=user.getUserPassword();
        String planetCode=user.getPlanetCode();
//        String avatarUrl=user.getAvatarUrl();
//        int gender=user.getGender();
//        String phone=user.getPhone();
//        String email=user.getEmail();
//        int userRole=user.getUserRole();
//        int userStatus=user.getUserStatus();
//        Date createTime=user.getCreateTime();
        if(StringUtils.isAnyBlank(userAccount, userPassword,planetCode))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        boolean result=userService.save(user);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj=request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser=(User) userObj;
        if(currentUser==null)
            throw new BusinessException(ErrorCode.NO_LOGIN,"无用户信息");
        long id=currentUser.getId();
        //todo 校验用户是否合法
        User user=userService.getById(id);
        User safeUser=userService.getSafetyUser(user);
        return ResultUtils.success(safeUser);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        String userAccount=userLoginRequest.getUserAccount();
        String userPassword=userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        User user=userService.userLogin(userAccount, userPassword,request);
        return  ResultUtils.success(user);

    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request==null)
            throw new BusinessException(ErrorCode.NO_LOGIN,"用户未登录");
        int result=userService.userLogout(request);
        return ResultUtils.success(result);

    }
    @PostMapping("/search")
    public BaseResponse<List<User>> searchUsers(@RequestBody User user,HttpServletRequest request){
        //鉴权
        if(!isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH,"当前用户无权限操作");
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        if(StringUtils.isNotBlank(user.getUsername())){
//            queryWrapper.like("username",user.getUsername());
//        }
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = ReflectionUtils.getField(field, user);

            if (fieldValue != null && StringUtils.isNotBlank(fieldValue.toString())) {
                queryWrapper.like(field.getName(), fieldValue.toString());
            }
        }
        List<User> userList=userService.list(queryWrapper);
        List<User> newUserList=userList.stream().map(user1->{
            return userService.getSafetyUser(user1);
        }).collect(Collectors.toList());
        return ResultUtils.success(newUserList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody User user,HttpServletRequest request){
        if(!isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH,"当前用户无权限操作");
        if(user.getId()<=0){
            throw new BusinessException(ErrorCode.NO_AUTH,"当前用户无权限操作");
        }
        boolean res=userService.removeById(user.getId());
        return ResultUtils.success(res);

    }

    /**
     * 单个修改
     * @return boolean
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody User user,HttpServletRequest request){
        if(!isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH,"当前用户无权限操作");
        if(user.getId()<=0){
            throw new BusinessException(ErrorCode.NO_AUTH,"当前用户无权限操作");
        }
        boolean res=userService.updateById(user);
        return ResultUtils.success(res);
    }


    private boolean isAdmin(HttpServletRequest request){
        Object userObject=request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user=(User) userObject;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
