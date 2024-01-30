package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.HomeDto;
import com.helpduk.helpDuk.base.dto.TaskDetailDto;
import com.helpduk.helpDuk.base.dto.TaskSearchDto;
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

    @PostMapping("/task") // 게시글 작성
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

    @GetMapping("/task/{taskId}") // 게시글 상세 보기
    public ResponseEntity<TaskDetailDto> getTaskDetail(@PathVariable Integer taskId){
        //  Integer userId = JwtUtil.getCurrentMemberId();
        // 일단 userId 1을 넣어줍니다. 방문자 아이디.
        Integer userId = 1;

        TaskDetailDto taskDetailDto = taskService.createTaskDetailDto(taskId, userId);

        return ResponseEntity.ok(taskDetailDto);
    }

    @PutMapping("/task/{taskId}") // 심부를 거래 현황 변경
    public ResponseEntity<String> updateTaskStatus(@PathVariable Integer taskId, @RequestBody String taskStatus) throws AccessDeniedException {

        // 일단 방문자가 1이라고 가정
        // Integer visitUserId = JwtUtil.getCurrentMemberId();
        Integer visitUserId = 1;

        taskService.updateTaskStatus(taskId, visitUserId, taskStatus);
        return ResponseEntity.ok("거래 현황 변경 완료");
    }

    @GetMapping("/home")
    public ResponseEntity<HomeDto> getHomePage(){

//        Integer userId = JwtUtil.getCurrentMemberId();
        // 사용자가 1이라고 가정 사용자의 프로필을 가져와야 하기 때문에 필요하다. 로그인을 하지 않을 경우를 고려해야 한다. -> 추후 개발 예정
        Integer userId = 1;
        HomeDto homeDto = taskService.getHomePage(userId);

        return ResponseEntity.ok(homeDto);
    }

    @GetMapping("/tasks/search")
    public ResponseEntity<TaskSearchDto> getSearchTask(@RequestParam(value = "keyword") String keyword){

        //        Integer userId = JwtUtil.getCurrentMemberId();
        // 사용자가 1이라고 가정 사용자의 프로필을 가져와야 하기 때문에 필요하다. 로그인을 하지 않을 경우를 고려해야 한다. -> 추후 개발 예정
        Integer userId = 1;
        TaskSearchDto taskSearchDto = taskService.getKeywordSearch(userId, keyword);

        return ResponseEntity.ok(taskSearchDto);
    }






}
