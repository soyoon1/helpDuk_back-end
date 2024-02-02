package com.helpduk.helpDuk.base.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskSearchDto {
    // 검색 기능에 반환될 Dto
    private String profileImage;
    private String keyword;
    private List<HomeTaskDto> taskList;
}
