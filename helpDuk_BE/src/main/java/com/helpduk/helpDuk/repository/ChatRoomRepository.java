package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer> {
    Integer countByTaskId(TaskEntity task);
}
