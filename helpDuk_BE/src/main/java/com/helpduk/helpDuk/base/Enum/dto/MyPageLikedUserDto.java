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

    private List<LikeDto> likedUserList;
}
