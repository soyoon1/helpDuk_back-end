package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.response.ResponseDTO;
import com.helpduk.helpDuk.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/create")
    public ResponseEntity<String> createLike(@RequestParam Integer userId, @RequestParam Integer likeUserId){

        likeService.createLike(userId, likeUserId);

        return ResponseEntity.ok("좋아요 생성 완료");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLike(@RequestParam Integer userId, @RequestParam Integer likeUserId){
        likeService.deleteLike(userId, likeUserId);

       return ResponseEntity.ok("좋아요 취소 완료");
    }


}
