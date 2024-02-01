package com.helpduk.helpDuk.service.impl;

import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// 예제 13.8
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    public UserEntity loadUserByUsername(String userId) {
        LOGGER.info("[loadUserByUsername] loadUserByUserId 수행. id : {}", userId);
        return userRepository.findByUserId(Integer.valueOf(userId));
    }
}