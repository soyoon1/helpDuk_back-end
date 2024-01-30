package com.helpduk.helpDuk.service;


import com.helpduk.helpDuk.base.Enum.dto.SignInResultDto;
import com.helpduk.helpDuk.base.Enum.dto.SignUpResultDto;

// 예제 13.24
public interface SignService {

    SignUpResultDto signUp(String userEmail, String password, String nickName, Float temperature);

    SignInResultDto signIn(String userEmail, String password) throws RuntimeException;

}