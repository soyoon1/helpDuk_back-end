package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.base.Enum.DetailCategory;
import com.helpduk.helpDuk.base.Enum.LocationCategory;
import com.helpduk.helpDuk.base.Enum.TaskStatus;
import com.helpduk.helpDuk.entity.ReviewEntity;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAllByOrderByUploadDateDesc();

    List<ReviewEntity> findByUser(UserEntity user);

    List<ReviewEntity> findByAcceptUser(UserEntity user);

}
