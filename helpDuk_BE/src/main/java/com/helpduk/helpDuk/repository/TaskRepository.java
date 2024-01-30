package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    Optional<TaskEntity> findByTaskId(Integer id);
    List<TaskEntity> findAllByOrderByUploadDateDesc();

    @Query("SELECT DISTINCT t FROM TaskEntity t WHERE t.title LIKE %:keyword% OR t.content LIKE %:keyword% ORDER BY t.uploadDate DESC")
    List<TaskEntity> findByTitleOrContentContainingOrderByUploadDateDesc(@Param("keyword") String keyword);

}
