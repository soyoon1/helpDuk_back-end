package com.helpduk.helpDuk.base.dto.chat;

import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListDto {

    private String roomId;
    private Integer taskId;
    private Integer helperId;
    private String nickName;
    private String profileImage;
    private String lastContent;

    public ChatRoomListDto(ChatRoomEntity chatRoom, UserEntity helper, String content) {
        this.roomId = chatRoom.getRoomId();
        this.helperId = helper.getUserId();
        this.nickName = helper.getNickName();;
        this.profileImage = helper.getProfileImage();
        this.lastContent = content;
    }
}
