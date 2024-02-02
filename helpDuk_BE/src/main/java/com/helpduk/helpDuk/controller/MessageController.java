package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.Enum.MessageType;
import com.helpduk.helpDuk.base.dto.chat.ChatMessageDto;
import com.helpduk.helpDuk.entity.MessageEntity;
import com.helpduk.helpDuk.service.ChatRoomService;
import com.helpduk.helpDuk.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;

    @MessageMapping("/chat/message")
    public void enter(MessageEntity message) {
        if (MessageType.ENTER.equals(message.getType())) {
//            message.setContent(message.getSenderId().getNickName()+"님이 입장하였습니다.");
            message.setContent("유저님이 입장하였습니다.");

        }

        messageService.saveMessage(message);
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }


    @GetMapping("/chat/getAllMessages")
    public List<ChatMessageDto> getAllMessages(@RequestParam String roomId){
        return messageService.getAllMessages(roomId);
    }
}