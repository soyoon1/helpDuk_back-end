package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.base.dto.chat.ChatMessageDto;
import com.helpduk.helpDuk.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

//    @Query("SELECT new com.example.ChatMessageDto(m.messageId, m.senderId, m.content, m.sendTime) " +
//            "FROM MessageEntity m WHERE m.roomId = :roomId")
//    List<ChatMessageDto> findChatMessagesByRoomId(@Param("roomId") String roomId);

    @Query("SELECT new com.helpduk.helpDuk.base.dto.chat.ChatMessageDto(m.messageId, m.senderId, m.content, m.sendTime) " +
            "FROM MessageEntity m WHERE m.roomId = :roomId")
    List<ChatMessageDto> findByRoomId(@Param("roomId") String roomId);

}
