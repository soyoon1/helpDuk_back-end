package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.UserRepository;
import com.helpduk.helpDuk.service.S3UploadService;
import com.helpduk.helpDuk.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final TaskService taskService;

    @PostMapping("/user") // 테스트 코드
    public ResponseEntity<String> createUser(){
        UserEntity user = UserEntity.builder()
                .userEmail("dodam@duksung.ac.kr")
                .nickName("도담")
                .password("20210841")
                .temperature(37.96F)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok("사용자 생성");
    }

    @PostMapping("/upload") // 테스트 코드
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try {
            String fileUrl = s3UploadService.saveFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PostMapping("/task")
//    public ResponseEntity<String> createTask(@RequestParam Integer userId,
//                                             @RequestParam("title") String title, @RequestParam("content") String content,
//                                             @RequestParam("locationCategory") String locaCategory, @RequestParam("detailCategory") String detaCategory,
//                                             @RequestParam("file") MultipartFile file, @RequestParam("taskTime") String taskTime,
//                                             @RequestParam("requestFee") Integer reqFee, @RequestParam("requestFeeMethod") String reqFeeMeth,
//                                             @RequestParam("taskFee") Integer taskFee, @RequestParam("taskFeeMethod") String taskFeeMeth){
//        try {
//            // 파일 String으로 변환
//            String fileUrl = s3UploadService.saveFile(file);
//
//            taskService.createTask(userId, title, content, locaCategory, detaCategory, fileUrl, taskTime, reqFee, reqFeeMeth, taskFee, taskFeeMeth);
//
//            return ResponseEntity.ok("게시글 작성 완료");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PostMapping("/task")
    public ResponseEntity<String> createTaskMultipleImages(@RequestParam Integer userId,
                                                           @RequestParam("title") String title, @RequestParam("content") String content,
                                                           @RequestParam("locationCategory") String locaCategory, @RequestParam("detailCategory") String detaCategory,
                                                           @RequestParam("files") List<MultipartFile> files, @RequestParam("taskTime") String taskTime,
                                                           @RequestParam("requestFee") Integer reqFee, @RequestParam("requestFeeMethod") String reqFeeMeth,
                                                           @RequestParam("taskFee") Integer taskFee, @RequestParam("taskFeeMethod") String taskFeeMeth){
        try {
            // 파일 String으로 변환
            List<String> fileUrls = new ArrayList<>();
            for (MultipartFile file: files){
                String fileUrl = s3UploadService.saveFile(file);
                fileUrls.add(fileUrl);
            }

            taskService.createTask(userId, title, content, locaCategory, detaCategory, fileUrls, taskTime, reqFee, reqFeeMeth, taskFee, taskFeeMeth);

            return ResponseEntity.ok("게시글 작성 완료");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping("")


}
