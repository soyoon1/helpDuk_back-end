package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.dto.ReviewDetailDto;
import com.helpduk.helpDuk.base.dto.UserResponseDto;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReviewService reviewService;

    @Transactional
    public UserResponseDto getOtherUserInformation(Integer userId){

        UserEntity otherUser = userRepository.findByUserId(userId).orElseThrow();
        ReviewDetailDto reviewDetailDto = reviewService.getReviewDetail(userId);

        return UserResponseDto.builder()
                .nickName(otherUser.getNickName())
                .profileImage(otherUser.getProfileImage())
                .temperature(otherUser.getTemperature())
                .reviewCnt(reviewDetailDto.getReviewCnt())
                .reviewDtoList(reviewDetailDto.getReviewDtoList())
                .build();
    }

}
