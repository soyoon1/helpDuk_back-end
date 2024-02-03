package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.base.Enum.DetailCategory;
import com.helpduk.helpDuk.base.Enum.LocationCategory;
import com.helpduk.helpDuk.base.Enum.TaskStatus;
import com.helpduk.helpDuk.entity.LikeEntity;
import com.helpduk.helpDuk.entity.ReviewEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.helpduk.helpDuk.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    List<LikeEntity> findAllByOrderByUploadDateDesc();

    List<LikeEntity> findByUserId(Optional<UserEntity> user);

    Optional<LikeEntity> findByUserIdAndLikeUserId(UserEntity user, UserEntity likeUserId);

    Optional<LikeEntity> deleteByLikeId(Integer likeId);


}
