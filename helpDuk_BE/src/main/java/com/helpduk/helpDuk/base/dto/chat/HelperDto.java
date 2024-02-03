package com.helpduk.helpDuk.base.dto.chat;

import com.helpduk.helpDuk.entity.UserEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HelperDto {
    private Integer helperId;
    private String nickName;
    private String profileImage;

    public HelperDto(UserEntity helper){
        this.helperId = helper.getUserId();
        this.nickName = helper.getNickName();
        this.profileImage = helper.getProfileImage();
    }
}
