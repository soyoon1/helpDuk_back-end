package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.base.dto.chat.ChatMessageDto;
import com.helpduk.helpDuk.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Query("SELECT new com.helpduk.helpDuk.base.dto.chat.ChatMessageDto(m.messageId, m.senderId, m.content, m.sendTime) " +
            "FROM MessageEntity m WHERE m.roomId = :roomId")
    List<ChatMessageDto> findByRoomId(@Param("roomId") String roomId);

    @Query("SELECT m.content FROM MessageEntity m WHERE m.roomId = :roomId ORDER BY m.messageId DESC LIMIT 1")
    String findTopContentByRoomIdOrderedByMessageIdDesc(@Param("roomId") String roomId);

}
