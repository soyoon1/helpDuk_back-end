package com.helpduk.helpDuk.base.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskCategorySearchDto {
    // 카테고리 검색 시 반환되는 Dto
    private String profileImage;
    private TaskCategoryDto categoryDtoList;
    private List<HomeTaskDto> taskList;
}
