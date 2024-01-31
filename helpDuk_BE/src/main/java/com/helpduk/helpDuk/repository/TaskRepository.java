package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.base.Enum.DetailCategory;
import com.helpduk.helpDuk.base.Enum.LocationCategory;
import com.helpduk.helpDuk.base.Enum.TaskStatus;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
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

    List<TaskEntity> findByTaskStatus(TaskStatus taskStatus);

    List<TaskEntity> findByUser(UserEntity user);
    List<TaskEntity> findByLocationCategory(LocationCategory locationCategory);
    List<TaskEntity> findByDetailCategory(DetailCategory detailCategory);

    @Query("SELECT t FROM TaskEntity t WHERE t.locationCategory IN :locationCategories AND t.detailCategory IN :detailCategories")
    List<TaskEntity> findByLocationCategoryInAndDetailCategoryIn(
            @Param("locationCategories") List<LocationCategory> locationCategories,
            @Param("detailCategories") List<DetailCategory> detailCategories
    );

    List<TaskEntity> findByLocationCategoryIn(List<LocationCategory> locationCategories);
    List<TaskEntity> findByDetailCategoryIn(List<DetailCategory> detailCategories);

}
