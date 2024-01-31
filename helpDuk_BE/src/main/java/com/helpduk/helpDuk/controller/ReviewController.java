package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.ReviewRequestDto;
import com.helpduk.helpDuk.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Slf4j
@RequestMapping("/api/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<String> createReview(@RequestBody ReviewRequestDto reviewRequestDto) throws AccessDeniedException {
        //        Integer userId = JwtUtil.getCurrentMemberId();
        // 사용자가 1이라고 가정 사용자의 프로필을 가져와야 하기 때문에 필요하다. 로그인을 하지 않을 경우를 고려해야 한다. -> 추후 개발 예정
        Integer userId = 1;
        reviewService.createReview(userId, reviewRequestDto);

        return ResponseEntity.ok("후기 저장 완료");
    }
}
