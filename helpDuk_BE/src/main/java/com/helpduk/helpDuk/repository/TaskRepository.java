package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    Optional<TaskEntity> findByTaskId(Integer id);
}
