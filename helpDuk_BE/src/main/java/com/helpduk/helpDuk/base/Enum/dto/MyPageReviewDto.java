package com.helpduk.helpDuk.base.Enum.dto;

import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MyPageReviewDto {
    private String nickName;
    private String profileImage;
    private String content;
    private Integer reviewId;
    private UserEntity user;
    private TaskEntity task;
    private UserEntity acceptUser;

    private List<MyPageReviewDto> feedbackList;
}
