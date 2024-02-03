package com.helpduk.helpDuk.service.impl;

import com.helpduk.helpDuk.base.Enum.DetailCategory;
import com.helpduk.helpDuk.base.Enum.LocationCategory;
import com.helpduk.helpDuk.base.Enum.TaskStatus;
import com.helpduk.helpDuk.base.Enum.dto.*;
import com.helpduk.helpDuk.common.CommonResponse;
import com.helpduk.helpDuk.config.security.JwtTokenProvider;
import com.helpduk.helpDuk.entity.LikeEntity;
import com.helpduk.helpDuk.entity.ReviewEntity;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.LikeRepository;
import com.helpduk.helpDuk.repository.ReviewRepository;
import com.helpduk.helpDuk.repository.TaskRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import com.helpduk.helpDuk.service.MyPageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final Logger LOGGER = LoggerFactory.getLogger(MyPageServiceImpl.class);
    public final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public MyPageDto updateProfile(Integer userId, String nickName, String profileImage) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update the nickname and profileImage.
        user.setNickName(nickName);
        user.setProfileImage(profileImage);

        // Save the updated user entity.
        userRepository.save(user);
        LOGGER.info("[프로필 수정] id : {} , nickname : {} , profile : {}", userId, nickName, profileImage);

        // Prepare and return the updated data as DTO
        return MyPageDto.builder()
                .userEmail(user.getUserEmail())
                .nickName(nickName)
                .temperature(String.valueOf(user.getTemperature()))
                .profileImage(profileImage)
                .build();
    }

    @Transactional
    public MyPageDto getMyPage(Integer userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        MyPageDto myPageDto = MyPageDto.builder()
                .userEmail(user.getUserEmail())
                .profileImage(user.getProfileImage())
                .nickName(user.getNickName())
                .temperature(String.valueOf(user.getTemperature()))
                .build();

//        LOGGER.info("[getMyPage] userEntity 값이 들어왔는지 확인 후 결과값 주입");
//        if (!myPageDto.getUserEmail().isEmpty()) {
//            LOGGER.info("[getMyPage] 정상 처리 완료");
//            setSuccessResult(myPageDto);
//        } else {
//            LOGGER.info("[getMyPage] 실패 처리 완료");
//            setFailResult(myPageDto);
//        }
        return myPageDto;
    }

    @Override
    public MyPageLikedUserDto getMyLikePage(Integer userId) {

        List<LikeDto> likedUserList = getMyPageLikedUserList(userId);

        return MyPageLikedUserDto.builder()
                .likedUserList(likedUserList)
                .build();
    }

    @Override
    public MyPageReviewDto getMyReviewPage(Integer userId) {

        List<ReviewDto> reviewList = getMyPageReviewList(userId);

        return MyPageReviewDto.builder()
                .feedbackList(reviewList)
                .build();
    }

    @Override
    public MyPageTaskDto getMyTaskPage(Integer userId) {

        List<TaskDto> taskList = getMyPageTastkList(userId);

        return MyPageTaskDto.builder()
                .taskList(taskList)
                .build();
    }

    @Override
    public MyPageTaskDto getOtherTaskPage(Integer userId) {

        List<TaskDto> taskList = getMyPageOtherTastkList(userId);

        return MyPageTaskDto.builder()
                .taskList(taskList)
                .build();
    }

    // 마이페이지 화면에서 의뢰 목록 가져오기
    @Transactional
    public List<TaskDto> getMyPageTastkList(Integer userId){
        return makeMyPageTaskDtoList(taskRepository.findByUser(userRepository.findByUserId(userId)));
    }

    @Transactional
    public List<TaskDto> getMyPageOtherTastkList(Integer userId){
        return makeMyPageTaskDtoList(taskRepository.findByAcceptUser(userRepository.findByUserId(userId)));
    }

    // 마이페이지 화면에서 후기 목록 가져오기
    @Transactional
    public List<ReviewDto> getMyPageReviewList(Integer userId){
        return makeMyPageReviewDtoList(reviewRepository.findByUser(userRepository.findByUserId(userId)));
    }

    // 마이페이지 화면에서 즐겨찾기 목록 가져오기
    @Transactional
    public List<LikeDto> getMyPageLikedUserList(Integer userId){
        return makeMyPageLikedUserDtoList(likeRepository.findByUserId(userRepository.findByUserId(userId)));
    }

    // List<TaskEntity>를 List<MyPageTaskDto>로 변환시켜주는 함수
    @Transactional
    public List<TaskDto> makeMyPageTaskDtoList(List<TaskEntity> taskEntityList){

        List<TaskDto> taskList = new ArrayList<>();

        for(TaskEntity task: taskEntityList){

            // 카테고리 String 타입으로 합침
            LocationCategory locCat = task.getLocationCategory();
            DetailCategory detCat = task.getDetailCategory();
            String category = combineCategory(locCat, detCat);

            // 시간을 2024-01-30 12:12:00 형식에 맞게 String 타입으로 변환
            LocalDateTime uploadDate = task.getUploadDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedUploadDate = uploadDate.format(formatter);

            // 이미지가 없는 경우를 고려 -> 만약 이미지가 없다면 null이 반환되도록 함.
            String firstImage = null;
            if(!task.getImage().isEmpty()){
                firstImage =task.getImage().get(0);
            }

            TaskDto myPageTaskDto = TaskDto.builder()
                    .taskId(task.getTaskId())
                    .title(task.getTitle())
                    .imageUrl(firstImage)
                    .taskStatus(enumToStringTaskStatus(task.getTaskStatus()))
                    .content(task.getContent())
                    .category(category)
                    .uploadDate(formattedUploadDate)
                    .requestFee(task.getRequestFee())
                    .build();

            taskList.add(myPageTaskDto);
        };

        return taskList;
    }

    // List<ReviewEntity>를 List<MypageReviewDto>로 변환시켜주는 함수
    @Transactional
    public List<ReviewDto> makeMyPageReviewDtoList(List<ReviewEntity> reviewEntityList){
        List<ReviewDto> reviewList = new ArrayList<>();

        // DB에 있는 모든 Review를 최신순으로 가져와 원하는 정보만을 추출
        for(ReviewEntity review: reviewEntityList){

            ReviewDto mypageReviewDto = ReviewDto.builder()
                    .reviewId(review.getReviewId())
                    .user(review.getUser())
                    .acceptUser(review.getAcceptUser())
                    .nickName(review.getNickname())
                    .profileImage(review.getProfileImage())
                    .task(review.getTask())
                    .content(review.getContent())
                    .build();

            reviewList.add(mypageReviewDto);
        };

        return reviewList;
    }

    // List<UserEntity>를 List<MypageLikedUserDto>로 변환시켜주는 함수
    @Transactional
    public List<LikeDto> makeMyPageLikedUserDtoList(List<LikeEntity> userEntityList){

        List<LikeDto> likeList = new ArrayList<>();

        // DB에 있는 모든 LikedUser를 최신순으로 가져와 원하는 정보만을 추출
        for(LikeEntity user: userEntityList){

            LikeDto mypageLikedUserDto = LikeDto.builder()
                    .likeId(user.getLikeId())
                    .userId(user.getUserId())
                    .likeUserId(user.getLikeUserId())
                    .build();

            likeList.add(mypageLikedUserDto);
        };

        return likeList;
    }


    @Transactional // 두 카테고리를 결합해 보기 좋게 String 타입으로 변환
    public String combineCategory(LocationCategory locationCategory, DetailCategory detailCategory){
        String locCat = enumToStringLocationCategory(locationCategory);
        String detCat = enumToStringDetailCategory(detailCategory);
        return locCat + ", " + detCat;
    }

    // Enum과 String 타입 변환
    @Transactional
    public String enumToStringLocationCategory(LocationCategory locCategory){
        return switch (locCategory) {
            case SCHOOL-> "학교 안" ;
            case DORMITORY -> "기숙사";
            case ETC -> "기타";
        };
    }

    @Transactional
    public String enumToStringDetailCategory(DetailCategory detCategory){
        // 디테일 카테고리 설정 타입 변환
        return switch (detCategory){
            case PRINT -> "프린트";
            case FOOD -> "음식";
            case COVFR -> "알바 대타";
            case CLEAN -> "청소";
            case EVTAST -> "행사 보조";
            case BUG -> "벌레";
        };
    }

    @Transactional
    public String enumToStringTaskStatus(TaskStatus taskStatus){
        return switch(taskStatus){
            case YET -> "거래 전";
            case RESERVATION -> "예약 중";
            case DONE -> "거래 완료";
        };
    }

//    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
//    private void setSuccessResult(MyPageDto result) {
//        result.setSuccess(true);
//        result.setCode(CommonResponse.SUCCESS.getCode());
//        result.setMsg(CommonResponse.SUCCESS.getMsg());
//    }
//
//    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
//    private void setFailResult(MyPageDto result) {
//        result.setSuccess(false);
//        result.setCode(CommonResponse.FAIL.getCode());
//        result.setMsg(CommonResponse.FAIL.getMsg());
//    }

}
