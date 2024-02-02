package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.ReviewDetailDto;
import com.helpduk.helpDuk.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.helpduk.helpDuk.base.Enum.dto.*;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.repository.UserRepository;
import com.helpduk.helpDuk.service.MyPageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MypageController {

    private final ReviewService reviewService;

    private final Logger LOGGER = LoggerFactory.getLogger(MypageController.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final MyPageService myPageService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/mypage")
    public ResponseEntity<MyPageDto> getMyPage(HttpServletRequest request){
//        Integer userId = JwtTokenProvider.authenticatedUser();
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageDto myPageDto = myPageService.getMyPage(userId);

        if(myPageDto == null){
            LOGGER.info("[getMyPage] 마이페이지 접속 실패");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        LOGGER.info("[getMyPage] 마이페이지 접속 성공");
        return ResponseEntity.ok(myPageDto);
    }

    @PostMapping("/mypage/edit")
    public ResponseEntity<String> editProfile(@RequestParam("nickName") String nickname,
                                              @RequestParam("profileImage") String profileImage){

//        Integer userId = JwtTokenProvider.authenticatedUser();
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        myPageService.updateProfile(userId, nickname, profileImage);

        //세션 등록
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                userRepository.findByUserId(userId).getUserEmail(),
//                userRepository.findByUserId(userId).getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("프로필 수정 완료");

    }

    @GetMapping("/mypage/like")
    public ResponseEntity<MyPageLikedUserDto> getMyLikePage(){

//        Integer userId = JwtTokenProvider.authenticatedUser();
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageLikedUserDto mypageLikedUserDto = myPageService.getMyLikePage(userId);

        return ResponseEntity.ok(mypageLikedUserDto);
    }

    @GetMapping("/mypage/review")
    public ResponseEntity<MyPageReviewDto> getMyReviewPage(){
//        Integer userId = JwtTokenProvider.authenticatedUser();
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageReviewDto myPageReviewDto = myPageService.getMyReviewPage(userId);

//        LOGGER.info("[getMyReviewPage]");

        if(myPageReviewDto == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.ok(myPageReviewDto);
    }

    @GetMapping("/mypage/task")
    public ResponseEntity<MyPageTaskDto> getMyTaskPage(){
//        Integer userId = JwtTokenProvider.authenticatedUser();
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageTaskDto myPageTaskDto = myPageService.getMyTaskPage(userId);

        return ResponseEntity.ok(myPageTaskDto);
    }

    @GetMapping("/mypage/othertask")
    public ResponseEntity<MyPageTaskDto> getOtherTaskPage(){
//        Integer userId = JwtTokenProvider.authenticatedUser();
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        MyPageTaskDto myPageTaskDto = myPageService.getOtherTaskPage(userId);

        return ResponseEntity.ok(myPageTaskDto);

    }
    @GetMapping("/mypage/review")
    public ResponseEntity<ReviewDetailDto> getReviewDetail() {
        //        Integer userId = JwtUtil.getCurrentMemberId();
        // 사용자가 3이라고 가정 사용자의 프로필을 가져와야 하기 때문에 필요하다. 로그인을 하지 않을 경우를 고려해야 한다. -> 추후 개발 예정
        Integer userId = 3;
        ReviewDetailDto reviewDetailDto = reviewService.getReviewDetail(userId);

        return ResponseEntity.ok(reviewDetailDto);
    }

}
