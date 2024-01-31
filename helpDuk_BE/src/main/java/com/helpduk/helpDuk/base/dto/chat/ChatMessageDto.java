package com.helpduk.helpDuk.base.dto.chat;

import com.helpduk.helpDuk.base.Enum.MessageType;
import com.helpduk.helpDuk.entity.UserEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private MessageType type;
    private String roomId; // 방 번호
    private Integer senderId; // 채팅을 보낸 사람
    private String content; // 메시지
}