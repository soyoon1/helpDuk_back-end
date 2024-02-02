package com.helpduk.helpDuk.base.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HomeTaskDto {
    // 홈페이지 심부름 글 내용
    private Integer taskId;
    private String title;
    private String imageUrl;
    private String taskStatus;
    private String content;
    private String category;
    private String uploadDate; // 2024-01-30 01:58 형식으로 변환 후 String 타입으로 변환시켜야 함.
    private Integer requestFee;

}
