package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.dto.chat.ChatMessageDto;
import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.MessageEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
import com.helpduk.helpDuk.repository.MessageRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    public void saveMessage(MessageEntity message){
        messageRepository.save(message);
    }
}
