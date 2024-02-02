package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.entity.LikeEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.LikeRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createLike(Integer userId, Integer likeUserId){
        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        UserEntity likeUser = userRepository.findByUserId(likeUserId).orElseThrow();

        LikeEntity like = LikeEntity.builder()
                .userId(user)
                .likeUserId(likeUser)
                .build();

        likeRepository.save(like);

    }

    @Transactional
    public void deleteLike(Integer userId, Integer likeUserId){
        UserEntity user = userRepository.findByUserId(userId).orElseThrow();;
        UserEntity likeUser = userRepository.findByUserId(likeUserId).orElseThrow();

        LikeEntity like = likeRepository.findByUserIdAndLikeUserId(user, likeUser).orElseThrow();

        likeRepository.deleteByLikeId(like.getLikeId());
    }
}
