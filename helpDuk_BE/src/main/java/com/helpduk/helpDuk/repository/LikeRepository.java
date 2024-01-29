package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.LikeEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserIdAndLikeUserId(UserEntity user, UserEntity likeUserId);

    Optional<LikeEntity> deleteByLikeId(Integer likeId);

}
