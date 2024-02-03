package com.helpduk.helpDuk.base.Enum.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskDto {
    private Integer taskId;
    private String title;
    private String imageUrl;
    private String taskStatus;
    private String content;
    private String category;
    private String uploadDate;
    private Integer requestFee;
}
