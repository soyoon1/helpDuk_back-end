package com.helpduk.helpDuk.base.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private Integer roomId; // 추후 String 타입으로 개발 예정
    private Integer score;
    private String content;


}
