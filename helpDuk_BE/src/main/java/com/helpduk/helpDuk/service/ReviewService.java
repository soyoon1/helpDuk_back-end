package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.Enum.DetailCategory;
import com.helpduk.helpDuk.base.Enum.TaskStatus;
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
        if(userRepository.findByUserId(userId).orElseThrow() != room.getUserId()){
            throw new AccessDeniedException("You do not have permission to update this task.");
        }

        // 해당 의뢰글에 accept_user_id 추가해 줌. + 거래 현황을 거래 완료로 바꿔 줌.
        TaskEntity task = room.getTaskId();
        task.setAcceptUser(room.getHelperUserId());
        task.setTaskStatus(TaskStatus.DONE);
        taskRepository.save(task);

        // 헬퍼의 temperature 업데이트
        UserEntity helperUser = room.getHelperUserId();
        updateUserTemperature(helperUser, reviewRequestDto.getScore());

        // 리뷰 생성
        ReviewEntity review = ReviewEntity.builder()
                .content(reviewRequestDto.getContent())
                .acceptUser(room.getHelperUserId())
                .task(room.getTaskId())
                .user(room.getUserId())
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

        Float updateTemp = userOriginTemp + plusTemp;

        helperUser.setTemperature(updateTemp);
        userRepository.save(helperUser);
    }

}
