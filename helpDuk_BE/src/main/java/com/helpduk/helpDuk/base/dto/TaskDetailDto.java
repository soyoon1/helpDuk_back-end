package com.helpduk.helpDuk.base.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskDetailDto {
    private List<String> imageUrl;
    private String nickName;
    private String profileImage;
    private Float temperature;
    private String title;
    private String category; // TaskService에서 위치, 디테일 카테고리를 합친 후 String 타입으로 변환시켜줘야 함.
    private String uploadDate; // 2024-01-30 01:58 형식으로 변환 후 String 타입으로 변환시켜야 함.
    private Integer requestFee;
    private String requestFeeMethod; // TaskService에서 타입 변환해야 함.
    private Integer taskFee;
    private String taskFeeMethod; // TaskService에서 타입 변환해야 함.
    private String taskTime;
    private String content;
    private Integer chattingCount;
    private String taskStatus; // TaskService에서 타입 변환해야 함.
    private Boolean isItMine;

}
