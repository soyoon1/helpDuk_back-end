package com.helpduk.helpDuk.base.dto.chat;

import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomInfoDto {

    private String roomId;
    private Integer taskId;
    private Integer userId;
    private HelperDto helper;

    public ChatRoomInfoDto(ChatRoomEntity chatRoom){
        this.roomId = chatRoom.getRoomId();
        this.taskId = chatRoom.getTask().getTaskId();
        this.userId = chatRoom.getUser().getUserId();
        this.helper = new HelperDto(chatRoom.getHelper());
    }

}
