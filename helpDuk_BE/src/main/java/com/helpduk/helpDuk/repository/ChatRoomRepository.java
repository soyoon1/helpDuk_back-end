package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
    Optional<ChatRoomEntity> findByRoomId(String id);
    List<ChatRoomEntity> findByUser(UserEntity user);
    Optional<ChatRoomEntity> findByUserAndHelper(UserEntity user, UserEntity helper);
    Integer countByTaskId(TaskEntity task);
}
