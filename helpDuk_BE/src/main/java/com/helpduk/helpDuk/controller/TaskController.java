package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.TaskDetailDto;
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
import java.nio.file.AccessDeniedException;
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

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskDetailDto> getTaskDetail(@PathVariable Integer taskId){
        //  Integer userId = JwtUtil.getCurrentMemberId();
        // 일단 userId 1을 넣어줍니다. 방문자 아이디.
        Integer userId = 1;

        TaskDetailDto taskDetailDto = taskService.createTaskDetailDto(taskId, userId);

        return ResponseEntity.ok(taskDetailDto);
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Integer taskId, @RequestBody String taskStatus) throws AccessDeniedException {

        // 일단 방문자가 1이라고 가정
        // Integer userId = JwtUtil.getCurrentMemberId();
        Integer visitUserId = 1;

        taskService.updateTaskStatus(taskId, visitUserId, taskStatus);
        return ResponseEntity.ok("거래 현황 변경 완료");
    }


}
