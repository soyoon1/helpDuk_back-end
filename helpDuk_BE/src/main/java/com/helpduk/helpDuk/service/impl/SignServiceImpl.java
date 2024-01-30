package com.helpduk.helpDuk.service.impl;

import com.helpduk.helpDuk.common.CommonResponse;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.base.Enum.dto.SignInResultDto;
import com.helpduk.helpDuk.base.Enum.dto.SignUpResultDto;
import com.helpduk.helpDuk.entity.User;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.UserRepository;
import com.helpduk.helpDuk.service.SignService;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// 예제 13.25
@Service
public class SignServiceImpl implements SignService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    @Autowired
    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(String userEmail, String password, String nickName, Float temperature) {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        user = User.builder()
                .nickName("익명의 유저")
                .password(passwordEncoder.encode(password))
                .profileImage(null)
                .temperature(36.5F)
                .userEmail(userEmail)
                .build();

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getUserEmail().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String userEmail, String password) throws RuntimeException {
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        User user = userRepository.getByUserEmail(userEmail);
        LOGGER.info("[getSignInResult] Email : {}", userEmail);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
            .token(jwtTokenProvider.createToken(String.valueOf(user.getUsername())))
            .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}