package com.helpduk.helpDuk.service.impl;

import com.helpduk.helpDuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

// 예제 13.8
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        LOGGER.info("[loadUserByUsername] loadUserByUserEmail 수행. userEmail : {}", userEmail);
        return userRepository.getByUserEmail(userEmail);
    }

}