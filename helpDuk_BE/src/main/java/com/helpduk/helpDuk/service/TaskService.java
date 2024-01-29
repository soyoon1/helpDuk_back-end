package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.Enum.*;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.TaskRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void createTask(Integer userId, String title, String content, String locaCategory,
                           String detaCategory, List<String> files, String taskTime, Integer reqFee, String reqFeeMeth, Integer taskFee, String taskFeeMeth){

        // 위치 카테고리 설정 타입 변환
        LocationCategory locationCategory = switch (locaCategory) {
            case "학교 안" -> LocationCategory.SCHOOL;
            case "기숙사" -> LocationCategory.DORMITORY;
            case "기타" -> LocationCategory.ETC;
            default -> null;
        };

        // 디테일 카테고리 설정 타입 변환
        DetailCategory detailCategory = switch (detaCategory){
            case "프린트" -> DetailCategory.PRINT;
            case "음식" -> DetailCategory.FOOD;
            case "알바 대타" -> DetailCategory.COVFR;
            case "청소" -> DetailCategory.CLEAN;
            case "행사 보조" -> DetailCategory.EVTAST;
            case "벌레" -> DetailCategory.BUG;
            default ->  null;
        };

        // 의뢰 비용 지불 방식 타입 변환
        RequestFeeMethod requestFeeMethod = switch (reqFeeMeth){
            case "계좌 이체" -> RequestFeeMethod.ACTF;
            case "현금" -> RequestFeeMethod.CASH;
            case "기타" -> RequestFeeMethod.ETC;
            case "해당 없음" -> RequestFeeMethod.NO;
            default ->  null;
        };

        // 심부름 비용 지불 방식 타입 변환
        TaskFeeMethod taskFeeMethod = switch (taskFeeMeth){
            case "심부름 전 계좌 이체" -> TaskFeeMethod.ACTF;
            case "의뢰비와 함께 지급" -> TaskFeeMethod.WHRF;
            case "해당 없음" -> TaskFeeMethod.NO;
            default -> null;
        };


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

}
