package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
}
