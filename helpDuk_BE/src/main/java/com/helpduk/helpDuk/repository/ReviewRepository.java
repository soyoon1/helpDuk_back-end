package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.ReviewEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    List<ReviewEntity> findByAcceptUser(UserEntity user);

}
