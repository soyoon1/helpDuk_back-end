package com.helpduk.helpDuk.base.Enum.dto;
import com.helpduk.helpDuk.entity.UserEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MyPageLikedUserDto {
    // 마이페이지 유저 즐겨찾기 내용
    private Integer likeId;
    private UserEntity userId;
    private UserEntity likeUserId;

    private List<MyPageLikedUserDto> likedUserList;
}
