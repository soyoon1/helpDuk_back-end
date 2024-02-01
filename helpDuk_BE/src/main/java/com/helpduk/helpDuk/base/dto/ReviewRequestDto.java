package com.helpduk.helpDuk.base.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private String roomId;
    private Integer score;
    private String content;


}
