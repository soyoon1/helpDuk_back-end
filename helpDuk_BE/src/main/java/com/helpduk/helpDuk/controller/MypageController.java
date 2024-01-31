package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.ReviewDetailDto;
import com.helpduk.helpDuk.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/mypage")
@RestController
@RequiredArgsConstructor
public class MypageController {

    private final ReviewService reviewService;

    @GetMapping("/review")
    public ResponseEntity<ReviewDetailDto> getReviewDetail(){
        //        Integer userId = JwtUtil.getCurrentMemberId();
        // 사용자가 3이라고 가정 사용자의 프로필을 가져와야 하기 때문에 필요하다. 로그인을 하지 않을 경우를 고려해야 한다. -> 추후 개발 예정
        Integer userId = 3;
        ReviewDetailDto reviewDetailDto = reviewService.getReviewDetail(userId);

        return ResponseEntity.ok(reviewDetailDto);
    }

}
