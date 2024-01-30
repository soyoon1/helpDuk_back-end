package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.Enum.*;
import com.helpduk.helpDuk.base.dto.TaskDetailDto;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
import com.helpduk.helpDuk.repository.TaskRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void createTask(Integer userId, String title, String content, String locaCategory,
                           String detaCategory, List<String> files, String taskTime, Integer reqFee, String reqFeeMeth, Integer taskFee, String taskFeeMeth){

        // 위치 카테고리 설정 타입 변환
        LocationCategory locationCategory = stringToEnumLocationCategory(locaCategory);

        // 디테일 카테고리 설정 타입 변환
        DetailCategory detailCategory = stringToEnumDetailCategory(detaCategory);

        // 의뢰 비용 지불 방식 타입 변환
        RequestFeeMethod requestFeeMethod = stringToEnumRequestFeeMethod(reqFeeMeth);

        // 심부름 비용 지불 방식 타입 변환
        TaskFeeMethod taskFeeMethod = stringToEnumTaskFeeMethod(taskFeeMeth);

        UserEntity user = userRepository.findByUserId(userId).orElseThrow();

        TaskEntity task = TaskEntity.builder()
                .title(title)
                .content(content)
                .taskStatus(TaskStatus.YET)
                .locationCategory(locationCategory)
                .detailCategory(detailCategory)
                .image(files)
                .taskTime(taskTime)
                .requestFee(reqFee)
                .requestFeeMethod(requestFeeMethod)
                .taskFee(taskFee)
                .taskFeeMethod(taskFeeMethod)
                .userId(user)
                .build();

        taskRepository.save(task);
    }

    @Transactional
    public TaskDetailDto createTaskDetailDto(Integer taskId, Integer userId){ // 방문자 아이디가 들어옴.
        // 작성자와 방문자를 구분해야 함. 작성자 아이디는 task의 userId를 사용해야 함.

        TaskEntity task = taskRepository.findByTaskId(taskId).orElse(null);

        UserEntity user = userRepository.findByUserId(userId).orElse(null); // 방문자

        // 카테고리 String 타입으로 합침
        LocationCategory locCat = task.getLocationCategory();
        DetailCategory detCat = task.getDetailCategory();

        String category = combineCategory(locCat, detCat);

        // 시간을 2024-01-30 12:12:00 형식에 맞게 String 타입으로 변환
        LocalDateTime uploadDate = task.getUploadDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedUploadDate = uploadDate.format(formatter);


        return TaskDetailDto.builder()
                .imageUrl(task.getImage())
                .nickName(task.getUserId().getNickName())
                .profileImage(task.getUserId().getProfileImage())
                .temperature(task.getUserId().getTemperature())
                .title(task.getTitle())
                .category(category)
                .uploadDate(formattedUploadDate)
                .requestFee(task.getRequestFee())
                .requestFeeMethod(enumToStringRequestFeeMethod(task.getRequestFeeMethod()))
                .taskFee(task.getTaskFee())
                .taskFeeMethod(enumToStringTaskFeeMethod(task.getTaskFeeMethod()))
                .taskTime(task.getTaskTime())
                .content(task.getContent())
                .chattingCount(chatRoomRepository.countByTaskId(task))
                .taskStatus(enumToStringTaskStatus(task.getTaskStatus()))
                .isItMine(task.getUserId().getUserId().equals(userId))
                .build();
    }

    @Transactional
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
    public LocationCategory stringToEnumLocationCategory(String locCategory){
        // 위치 카테고리 설정 타입 변환
        return switch (locCategory) {
            case "학교 안" -> LocationCategory.SCHOOL;
            case "기숙사" -> LocationCategory.DORMITORY;
            case "기타" -> LocationCategory.ETC;
            default -> null;
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
    public DetailCategory stringToEnumDetailCategory(String detCategory){
        // 디테일 카테고리 설정 타입 변환
         return switch (detCategory){
            case "프린트" -> DetailCategory.PRINT;
            case "음식" -> DetailCategory.FOOD;
            case "알바 대타" -> DetailCategory.COVFR;
            case "청소" -> DetailCategory.CLEAN;
            case "행사 보조" -> DetailCategory.EVTAST;
            case "벌레" -> DetailCategory.BUG;
            default ->  null;
        };
    }

    @Transactional
    public String enumToStringRequestFeeMethod(RequestFeeMethod reqFeeMeth){
        // 의뢰 비용 지불 방식 타입 변환
        return switch (reqFeeMeth){
            case ACTF -> "계좌 이체";
            case CASH -> "현금";
            case ETC -> "기타";
            case NO -> "해당 없음";
        };
    }

    @Transactional
    public RequestFeeMethod stringToEnumRequestFeeMethod(String reqFeeMeth){
        // 의뢰 비용 지불 방식 타입 변환
        return switch (reqFeeMeth){
            case "계좌 이체" -> RequestFeeMethod.ACTF;
            case "현금" -> RequestFeeMethod.CASH;
            case "기타" -> RequestFeeMethod.ETC;
            case "해당 없음" -> RequestFeeMethod.NO;
            default ->  null;
        };
    }

    @Transactional
    public String enumToStringTaskFeeMethod(TaskFeeMethod taskFeeMeth){
        // 심부름 비용 지불 방식 타입 변환
        return switch (taskFeeMeth){
            case ACTF -> "심부름 전 계좌 이체";
            case WHRF -> "의뢰비와 함께 지급";
            case NO -> "해당 없음";
        };
    }

    @Transactional
    public TaskFeeMethod stringToEnumTaskFeeMethod(String taskFeeMeth){
        // 심부름 비용 지불 방식 타입 변환
        return switch (taskFeeMeth){
            case "심부름 전 계좌 이체" -> TaskFeeMethod.ACTF;
            case "의뢰비와 함께 지급" -> TaskFeeMethod.WHRF;
            case "해당 없음" -> TaskFeeMethod.NO;
            default -> null;
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

    @Transactional
    public TaskStatus StringToEnumTaskStatus(String taskStatus){
        return switch (taskStatus){
            case "거래 전" -> TaskStatus.YET;
            case "예약 중" -> TaskStatus.RESERVATION;
            case "거래 완료" -> TaskStatus.DONE;
            default -> null;
        };
    }

}
