package com.hq.hqusercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author hq
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long  serializableUID=3191241716373120793L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
