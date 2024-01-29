package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserId(Integer id);

}
