package com.helpduk.helpDuk.base.Enum.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

    private Integer userId;
    private String profileImage;
    private String nickName;
    private String content;

}