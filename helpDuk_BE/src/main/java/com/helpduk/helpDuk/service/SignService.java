package com.helpduk.helpDuk.service;


import com.helpduk.helpDuk.base.Enum.dto.SignInResultDto;
import com.helpduk.helpDuk.base.Enum.dto.SignUpRequestDto;
import com.helpduk.helpDuk.base.Enum.dto.SignUpResultDto;

import java.util.Map;

// 예제 13.24
public interface SignService {

    SignUpResultDto signUp(String userEmail, String password, String nickName, Float temperature) throws Exception;

    SignInResultDto signIn(String userEmail, String password) throws RuntimeException;

//    public void logout(SignInResultDto tokenRequestDto);
}