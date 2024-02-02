package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.base.Enum.MessageType;
import com.helpduk.helpDuk.base.dto.chat.ChatMessageDto;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.entity.MessageEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.UserRepository;
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
    private final UserRepository userRepository;

    @MessageMapping("/chat/message")
    public void enter(MessageEntity message) {
        if (MessageType.ENTER.equals(message.getType())) {
            Integer userId = JwtTokenProvider.getCurrentMemberId();

            UserEntity user = userRepository.findById(userId).orElseThrow();
            message.setContent(user.getNickName() + " 님이 입장하였습니다.");
        }

        messageService.saveMessage(message);
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }


    @GetMapping("/chat/getAllMessages")
    public List<ChatMessageDto> getAllMessages(@RequestParam String roomId){
        Integer userId = JwtTokenProvider.getCurrentMemberId();

        return messageService.getAllMessages(userId, roomId);
    }
}