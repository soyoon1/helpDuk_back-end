package com.helpduk.helpDuk.base.Enum.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MyPageTaskDto {

    private List<TaskDto> taskList;
}
