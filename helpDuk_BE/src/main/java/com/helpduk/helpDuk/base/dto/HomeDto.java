package com.helpduk.helpDuk.base.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HomeDto {
    // 홈페이지에 반환할 Dto
    private String profileImage;
    private List<HomeTaskDto> taskList;
}
