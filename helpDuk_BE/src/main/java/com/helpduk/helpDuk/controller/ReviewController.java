package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.ReviewRequestDto;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
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
        Integer userId = JwtTokenProvider.getCurrentMemberId();
        reviewService.createReview(userId, reviewRequestDto);

        return ResponseEntity.ok("후기 저장 완료");
    }
}
