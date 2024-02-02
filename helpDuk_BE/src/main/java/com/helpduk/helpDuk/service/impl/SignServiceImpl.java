package com.helpduk.helpDuk.service.impl;

import com.helpduk.helpDuk.base.Enum.dto.SignUpRequestDto;
import com.helpduk.helpDuk.common.CommonResponse;
//import com.helpduk.helpDuk.config.MailUtil;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.base.Enum.dto.SignInResultDto;
import com.helpduk.helpDuk.base.Enum.dto.SignUpResultDto;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.UserRepository;
import com.helpduk.helpDuk.service.SignService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


// 예제 13.25
@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);
    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

//    private RedisTemplate<Object, Object> redisTemplate;

//    private final JavaMailSender javaMailSender;
//    private static final String senderEmail= "yuchaemin@duksung.ac.kr";
//    private static int number;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

//    @Transactional
//    public void logout(SignInResultDto signInResultDto){
//        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
//        if (!jwtTokenProvider.validateToken(signInResultDto.getToken())){
//            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
//        }
//
//        // Access Token에서 User email을 가져온다
//        Authentication authentication = jwtTokenProvider.getAuthentication(signInResultDto.getToken());
//
//        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        if (redisTemplate.opsForValue().get("RT:"+authentication.getName())!=null){
//            // Refresh Token을 삭제
//            redisTemplate.delete("RT:"+authentication.getName());
//        }
//
//        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
//        Long expiration = jwtTokenProvider.getExpiration(signInResultDto.getToken());
//        redisTemplate.opsForValue().set(signInResultDto.getToken(),"logout",expiration, TimeUnit.MILLISECONDS);
//    }

//    public static void createNumber(){
//        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
//    }
//
//    public MimeMessage CreateMail(String mail){
//        createNumber();
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        try {
//            message.setFrom(senderEmail);
//            message.setRecipients(MimeMessage.RecipientType.TO, mail);
//            message.setSubject("이메일 인증");
//            String body = "";
//            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
//            body += "<h1>" + number + "</h1>";
//            body += "<h3>" + "감사합니다." + "</h3>";
//            message.setText(body,"UTF-8", "html");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        return message;
//    }
//
//    public int sendMail(String mail){
//
//        MimeMessage message = CreateMail(mail);
//
//        javaMailSender.send(message);
//
//        return number;
//    }

//    @Override
//    public SignUpResultDto signUp(String userEmail, String password, String nickName, Float temperature) throws Exception {
//        LOGGER.info("[getSignUpResult] Delivery of membership registration information");
//
//        // Check if the email is already in use
//        if (userRepository.findByUserEmail(userEmail).isPresent()) {
//            throw new Exception("You have already signed up with this email.");
//        }
//
//        if (!userEmail.endsWith("@duksung.ac.kr")) {
//            throw new Exception("Only emails ending with @duksung.ac.kr are allowed to register.");
//        }
//
//        // Create a new UserEntity
//        UserEntity user = UserEntity.builder()
//                .nickName(nickName)
//                .password(passwordEncoder.encode(password)) // Ensure password is encoded
//                .profileImage(null) // Set profile image, if necessary
//                .temperature(36.5F) // Use provided temperature or default
//                .userEmail(userEmail)
//                .build();
//
//        // Save the new user entity
//        UserEntity savedUser = userRepository.save(user);
//
//        // Initialize the SignUpResultDto
//        SignUpResultDto signUpResultDto = new SignUpResultDto();
//
//        // Check if the user was saved successfully
//        if (savedUser != null && savedUser.getUserEmail() != null && !savedUser.getUserEmail().isEmpty()) {
//            LOGGER.info("[getSignUpResult] Normal processing completed");
//            setSuccessResult(signUpResultDto); // Assuming this method sets success response
//        } else {
//            LOGGER.info("[getSignUpResult] failure handling completed");
//            setFailResult(signUpResultDto); // Assuming this method sets failure response
//        }
//        return signUpResultDto;
//    }


    @Override
    public SignUpResultDto signUp(String userEmail, String password, String nickName, Float temperature) throws Exception {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");

        if(userRepository.findByUserEmail(userEmail).isPresent()){
            throw new Exception("이미 가입한 이메일입니다.");
        }

        if (!userEmail.endsWith("@duksung.ac.kr")) {
            throw new Exception("덕성 이메일만 가입할 수 있습니다.");
        }
            UserEntity user = new UserEntity();
            user = UserEntity.builder()
                    .nickName(nickName)
                    .password(passwordEncoder.encode(password))
                    .profileImage("https://helpdukbucket.s3.ap-northeast-2.amazonaws.com/%EB%94%94%EC%8A%A4%EC%BD%94%EB%93%9C+%EB%B0%B0%EA%B2%BD%ED%99%94%EB%A9%B4.PNG")
                    .temperature(36.5F)
                    .userEmail(userEmail)
                    .build();

        UserEntity savedUser = userRepository.save(user);

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

    @Transactional
    public SignInResultDto signIn(String userEmail, String password) throws RuntimeException {
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        Optional<UserEntity> userOptional = userRepository.findByUserEmail(userEmail);

        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found.");
        }

        UserEntity user = userOptional.get();
        LOGGER.info("[getSignInResult] Email : {}", userEmail);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
            .token(jwtTokenProvider.createToken(String.valueOf(user.getUserId())))
            .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

//    @Transactional
//    public void logout(SignInResultDto tokenRequestDto){
//        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
//        if (!jwtTokenProvider.validateToken(tokenRequestDto.getToken())){
//            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
//        }
//
//        // Access Token에서 User email을 가져온다
//        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getToken());
//
//        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
//        if (redisTemplate.opsForValue().get("RT:"+authentication.getName())!=null){
//            // Refresh Token을 삭제
//            redisTemplate.delete("RT:"+authentication.getName());
//        }
//
//        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
//        Long expiration = jwtTokenProvider.getExpiration(tokenRequestDto.getToken());
//        redisTemplate.opsForValue().set(tokenRequestDto.getToken(),"logout",expiration,TimeUnit.MILLISECONDS);
//
//    }

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