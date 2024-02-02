package com.helpduk.helpDuk.base.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReviewDetailDto {

    private Integer reviewCnt;

    private List<ReviewDto> reviewDtoList;

}