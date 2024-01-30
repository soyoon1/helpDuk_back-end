package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 예제 13.7
public interface UserRepository extends JpaRepository<User, Long> {

    User getByUserEmail(String userEmail);

}