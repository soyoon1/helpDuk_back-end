package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 예제 13.7
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

//    UserEntity findByUserId(Integer id);

    Optional<UserEntity> findByUserId(Integer id);
    Optional<UserEntity> findByUserEmail(String userEmail);
}