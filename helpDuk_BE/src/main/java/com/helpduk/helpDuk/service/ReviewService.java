package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.Enum.DetailCategory;
import com.helpduk.helpDuk.base.Enum.TaskStatus;
import com.helpduk.helpDuk.base.dto.ReviewDetailDto;
import com.helpduk.helpDuk.base.dto.ReviewDto;
import com.helpduk.helpDuk.base.dto.ReviewRequestDto;
import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.ReviewEntity;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
import com.helpduk.helpDuk.repository.ReviewRepository;
import com.helpduk.helpDuk.repository.TaskRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void createReview(Integer userId, ReviewRequestDto reviewRequestDto) throws AccessDeniedException {

        ChatRoomEntity room = chatRoomRepository.findById(reviewRequestDto.getRoomId()).orElseThrow();

        // 리뷰 작성자와 의뢰글 작성자가 같은지 확인. 같아야지만 리뷰를 작성할 수 있음.
        if(userRepository.findByUserId(userId).orElseThrow() != room.getUser()){
            throw new AccessDeniedException("You do not have permission to update this task.");
        }

        // 해당 의뢰글에 accept_user_id 추가해 줌. + 거래 현황을 거래 완료로 바꿔 줌.
        TaskEntity task = room.getTask();
        task.setAcceptUser(room.getHelper());
        task.setTaskStatus(TaskStatus.DONE);
        taskRepository.save(task);

        // 헬퍼의 temperature 업데이트
        UserEntity helperUser = room.getHelper();
        updateUserTemperature(helperUser, reviewRequestDto.getScore());

        // 리뷰 생성
        ReviewEntity review = ReviewEntity.builder()
                .content(reviewRequestDto.getContent())
                .acceptUser(room.getHelper())
                .task(room.getTask())
                .user(room.getUser())
                .nickname(room.getUser().getNickName())
                .profileImage(room.getUser().getProfileImage())
                .build();

        reviewRepository.save(review);


    }

    @Transactional
    public void updateUserTemperature(UserEntity helperUser, Integer score){
        Float userOriginTemp = helperUser.getTemperature();

        Float plusTemp = switch (score){
            case 1 -> -3.6F;
            case 2 -> 1.3F;
            case 3 -> 4.5F;
            default ->  0F;
        };

        float updateTemp = userOriginTemp + plusTemp;

        // 최고 온도와 최저 온도를 설정해줘야 함.
        if (updateTemp > 100F)
            updateTemp = 100F;
        else if (updateTemp < 0F)
            updateTemp = 0F;

        helperUser.setTemperature(updateTemp);
        userRepository.save(helperUser);
    }

    // 마이페이지 리뷰 상세 페이지
    @Transactional
    public ReviewDetailDto getReviewDetail(Integer userId){

        // acceptUser와 매개변수 userId의 User이 동일한 Review 데이터 리스트를 가져옴
        List<ReviewEntity> reviewEntityList = reviewRepository.findByAcceptUser(userRepository.findByUserId(userId).orElseThrow());

        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for(ReviewEntity review: reviewEntityList){
            ReviewDto reviewDto = ReviewDto.builder()
                    .userId(review.getUser().getUserId())
                    .profileImage(review.getUser().getProfileImage())
                    .nickName(review.getUser().getNickName())
                    .content(review.getContent())
                    .build();
            reviewDtoList.add(reviewDto);
        }

        Integer reviewCnt = reviewDtoList.size();

        return ReviewDetailDto.builder()
                .reviewCnt(reviewCnt)
                .reviewDtoList(reviewDtoList)
                .build();
    }



}
