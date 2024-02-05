package com.helpduk.helpDuk.base.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    // 타회원 페이지에 반환될 내용
    private String nickName;
    private String profileImage;
    private Float temperature;

    private Integer reviewCnt;

    private List<ReviewDto> reviewDtoList;

    private boolean isLike;

}
