package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.UserResponseDto;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.service.ReviewService;
import com.helpduk.helpDuk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 타회원 페이지 보이기
    @GetMapping("")
    public ResponseEntity<UserResponseDto> getOtherUserInformation(@RequestParam Integer otherUserId){

        Integer currentUserId = JwtTokenProvider.getCurrentMemberId();

        UserResponseDto userResponseDto = userService.getOtherUserInformation(currentUserId, otherUserId);

        return ResponseEntity.ok(userResponseDto);
    }
}
