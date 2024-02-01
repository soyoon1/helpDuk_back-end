package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.Enum.dto.*;
import com.helpduk.helpDuk.common.CommonResponse;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.config.security.JwtUtil;
import com.helpduk.helpDuk.repository.UserRepository;
import com.helpduk.helpDuk.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MypageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MypageController.class);
    private final UserRepository userRepository;
    private final MyPageService myPageService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/mypage")
    public ResponseEntity<MyPageDto> getMyPage(){
//        Integer userId = JwtUtil.getCurrentMemberId(jwtTokenProvider.resolveToken(request));
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageDto myPageDto = myPageService.getMyPage(userId);
        return ResponseEntity.ok(myPageDto);
    }

    @PostMapping("/mypage/edit")
    public ResponseEntity<String> editProfile(@RequestParam("nickName") String nickname,
                                              @RequestParam("profileImage") String profileImage){

        Integer userId = JwtTokenProvider.getCurrentMemberId();

        myPageService.updateProfile(userId, nickname, profileImage);

        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRepository.findByUserId(userId).getUserEmail(),
                userRepository.findByUserId(userId).getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("프로필 수정 완료");

    }

    @GetMapping("/mypage/like")
    public ResponseEntity<MyPageLikedUserDto> getMyLikePage(){

//        Integer userId = JwtUtil.getCurrentMemberId(jwtTokenProvider.resolveToken(request));
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageLikedUserDto mypageLikedUserDto = myPageService.getMyLikePage(userId);

        return ResponseEntity.ok(mypageLikedUserDto);
    }

    @GetMapping("/mypage/review")
    public ResponseEntity<MyPageReviewDto> getMyReviewPage(){
//        Integer userId = JwtUtil.getCurrentMemberId(jwtTokenProvider.resolveToken(request));
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageReviewDto myPageReviewDto = myPageService.getMyReviewPage(userId);

        return ResponseEntity.ok(myPageReviewDto);
    }

    @GetMapping("/mypage/task")
    public ResponseEntity<MyPageTaskDto> getMyTaskPage(){
//        Integer userId = JwtUtil.getCurrentMemberId(jwtTokenProvider.resolveToken(request));
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageTaskDto myPageTaskDto = myPageService.getMyTaskPage(userId);

        return ResponseEntity.ok(myPageTaskDto);
    }

    @GetMapping("/mypage/othertask")
    public ResponseEntity<MyPageTaskDto> getOtherTaskPage(){
//        Integer userId = JwtUtil.getCurrentMemberId(jwtTokenProvider.resolveToken(request));
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageTaskDto myPageTaskDto = myPageService.getOtherTaskPage(userId);

        return ResponseEntity.ok(myPageTaskDto);
    }

}
