package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.Enum.dto.SignInResultDto;
import com.helpduk.helpDuk.base.Enum.dto.SignUpResultDto;
import com.helpduk.helpDuk.service.SignService;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 예제 13.28
@RestController
@RequestMapping("/api/sign")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(
        @ApiParam(value = "Email", required = true) @RequestParam String userEmail,
        @ApiParam(value = "Password", required = true) @RequestParam String password)
        throws RuntimeException {
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", userEmail);
        SignInResultDto signInResultDto = signService.signIn(userEmail, password);

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", userEmail,
                signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(
        @ApiParam(value = "Email", required = true) @RequestParam String userEmail,
        @ApiParam(value = "Password", required = true) @RequestParam String password ) {
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****,", userEmail);
        SignUpResultDto signUpResultDto = signService.signUp(userEmail, password, "익명의 유저", 36.5F);

        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", userEmail);
        return signUpResultDto;
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}