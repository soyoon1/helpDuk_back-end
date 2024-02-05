package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.dto.ReviewDetailDto;
import com.helpduk.helpDuk.base.dto.UserResponseDto;
import com.helpduk.helpDuk.entity.LikeEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.LikeRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ReviewService reviewService;

    @Transactional
    public UserResponseDto getOtherUserInformation(Integer currentUserId, Integer otherUserId){

        UserEntity otherUser = userRepository.findByUserId(otherUserId).orElseThrow();
        ReviewDetailDto reviewDetailDto = reviewService.getReviewDetail(otherUserId);
        boolean isLike = likeRepository.findByUserIdAndLikeUserId(userRepository.findByUserId(currentUserId).orElseThrow(), otherUser).isPresent();

        return UserResponseDto.builder()
                .nickName(otherUser.getNickName())
                .profileImage(otherUser.getProfileImage())
                .temperature(otherUser.getTemperature())
                .reviewCnt(reviewDetailDto.getReviewCnt())
                .reviewDtoList(reviewDetailDto.getReviewDtoList())
                .isLike(isLike)
                .build();
    }

    @Transactional
    public String getUserProfileImage(Integer userId){
        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        return user.getProfileImage();
    }

    @Transactional
    public String getUserNickName(Integer userId){
        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        return user.getNickName();
    }

}
