package com.helpduk.helpDuk.base.dto.chat;

import com.helpduk.helpDuk.base.Enum.MessageType;
import com.helpduk.helpDuk.entity.UserEntity;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private Integer messageId;
    private UserEntity senderId; // 채팅을 보낸 사람
    private String content; // 메시지
    private LocalDateTime sendTime;


}