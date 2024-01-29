package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer> {
}
