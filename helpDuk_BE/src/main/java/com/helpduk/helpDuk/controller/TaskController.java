package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.dto.*;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.entity.TaskEntity;
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
    public ResponseEntity<String> createTaskMultipleImages(
                                                           @RequestParam("title") String title, @RequestParam("content") String content,
                                                           @RequestParam("locationCategory") String locaCategory, @RequestParam("detailCategory") String detaCategory,
                                                           @RequestParam("files") List<MultipartFile> files, @RequestParam("taskTime") String taskTime,
                                                           @RequestParam("requestFee") Integer reqFee, @RequestParam("requestFeeMethod") String reqFeeMeth,
                                                           @RequestParam("taskFee") Integer taskFee, @RequestParam("taskFeeMethod") String taskFeeMeth){

        Integer userId = JwtTokenProvider.getCurrentMemberId();
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

        Integer userId = JwtTokenProvider.getCurrentMemberId();

        TaskDetailDto taskDetailDto = taskService.createTaskDetailDto(taskId, userId);

        return ResponseEntity.ok(taskDetailDto);
    }

    @PutMapping("/task/{taskId}") // 심부를 거래 현황 변경
    public ResponseEntity<String> updateTaskStatus(@PathVariable Integer taskId, @RequestBody String taskStatus) throws AccessDeniedException {

        Integer visitUserId = JwtTokenProvider.getCurrentMemberId();

        taskService.updateTaskStatus(taskId, visitUserId, taskStatus);
        return ResponseEntity.ok("거래 현황 변경 완료");
    }

    @GetMapping("/home") // 홈페이지
    public ResponseEntity<HomeDto> getHomePage(){

        Integer userId = JwtTokenProvider.getCurrentMemberId();
        HomeDto homeDto = taskService.getHomePage(userId);

        return ResponseEntity.ok(homeDto);
    }

    @GetMapping("/tasks/search") // 키워드 검색
    public ResponseEntity<TaskSearchDto> getSearchTask(@RequestParam(value = "keyword") String keyword){

        Integer userId = JwtTokenProvider.getCurrentMemberId();
        TaskSearchDto taskSearchDto = taskService.getKeywordSearch(userId, keyword);

        return ResponseEntity.ok(taskSearchDto);
    }

    @GetMapping("/tasks/category") // 카테고리 검색 -> 리팩토링해야 할 것 같음 ... @RequestParam 이 너무 많음.
    public ResponseEntity<TaskCategorySearchDto> getCategorySearchTask(@RequestParam(value = "onlyYet") boolean onlyYet, @RequestParam(value = "onlyMine") boolean onlyMine,
                                                                       @RequestParam(value = "school") boolean school, @RequestParam(value = "dormitory") boolean dormitory,
                                                                       @RequestParam(value = "etc") boolean etc, @RequestParam(value = "print") boolean print,
                                                                       @RequestParam(value = "food") boolean food, @RequestParam(value = "coverFor") boolean coverFor,
                                                                       @RequestParam(value = "clean") boolean clean, @RequestParam(value = "eventAssistant") boolean eventAssistant,
                                                                       @RequestParam(value = "bug") boolean bug){


        Integer userId = JwtTokenProvider.getCurrentMemberId();

        List<TaskEntity> taskList = taskService.getFilteredTasks(userId, onlyMine, school, dormitory, etc, print, food, coverFor, clean, eventAssistant, bug, onlyYet);


        TaskCategoryDto taskCategoryDto = TaskCategoryDto.builder()
                .onlyYet(onlyYet)
                .onlyMine(onlyMine)
                .school(school)
                .dormitory(dormitory)
                .etc(etc)
                .print(print)
                .food(food)
                .coverFor(coverFor)
                .clean(clean)
                .eventAssistant(eventAssistant)
                .bug(bug)
                .build();

        TaskCategorySearchDto taskCategorySearchDto = taskService.getCatSearchTask(userId, taskCategoryDto, taskList);

        return ResponseEntity.ok(taskCategorySearchDto);
    }






}
