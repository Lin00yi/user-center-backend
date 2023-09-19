package com.hq.hqusercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author hq
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long  serializableUID=3191241716373120793L;
    private String userAccount;
    private String userPassword;
}
