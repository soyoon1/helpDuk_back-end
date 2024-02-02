package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.Enum.*;
import com.helpduk.helpDuk.base.dto.*;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
import com.helpduk.helpDuk.repository.TaskRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ChatRoomRepository chatRoomRepository;

    // Task 생성 후 저장
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
                .user(user)
                .build();

        taskRepository.save(task);
    }

    // 상세 페이지
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
                .nickName(task.getUser().getNickName())
                .profileImage(task.getUser().getProfileImage())
                .temperature(task.getUser().getTemperature())
                .title(task.getTitle())
                .category(category)
                .uploadDate(formattedUploadDate)
                .requestFee(task.getRequestFee())
                .requestFeeMethod(enumToStringRequestFeeMethod(task.getRequestFeeMethod()))
                .taskFee(task.getTaskFee())
                .taskFeeMethod(enumToStringTaskFeeMethod(task.getTaskFeeMethod()))
                .taskTime(task.getTaskTime())
                .content(task.getContent())
                .chattingCount(chatRoomRepository.countByTask(task))
                .taskStatus(enumToStringTaskStatus(task.getTaskStatus()))
                .isItMine(task.getUser().getUserId().equals(userId))
                .build();
    }

    // 거래 현황 수정
    @Transactional
    public void updateTaskStatus(Integer taskId, Integer visitUserId, String taskStatus) throws AccessDeniedException {

        // enum 타입으로 변환
        TaskStatus status = stringToEnumTaskStatus(taskStatus);

        Optional<TaskEntity> optionalTask = taskRepository.findByTaskId(taskId);
        if(optionalTask.isPresent()){
            TaskEntity task = optionalTask.get();

            // 지금 방문자와 task의 작성자가 같은지를 판단하기
            if(task.getUser().getUserId().equals(visitUserId)){
                task.setTaskStatus(status);
                taskRepository.save(task);
            }else{
                // 작성자와 방문자가 다른 경우 거래 현황을 수정할 수 없도록 접근 거부 예외 던지기
                throw new AccessDeniedException("You do not have permission to update this task.");
            }
        }else{
            // 해당 taskId에 해당하는 Task가 없는 경우 예외처리
            throw new EntityNotFoundException("Task with ID " + taskId + " not found");
        }
    }

    // 홈페이지 화면
    @Transactional
    public HomeDto getHomePage(Integer userId){
        String profileImage = userRepository.findByUserId(userId).get().getProfileImage();

        List<HomeTaskDto> taskList = getHomeTaskList();

        return HomeDto.builder()
                .profileImage(profileImage)
                .taskList(taskList)
                .build();
    }

    // 홈페이지 화면에서 의뢰 목록 가져오기
    @Transactional
    public List<HomeTaskDto> getHomeTaskList(){

        return makeHomeTaskDtoList(taskRepository.findAllByOrderByUploadDateDesc());
    }

    // List<TaskEntity>를 List<HomeTaskDto>로 변환시켜주는 함수
    @Transactional
    public List<HomeTaskDto> makeHomeTaskDtoList(List<TaskEntity> taskEntityList){

        List<HomeTaskDto> taskList = new ArrayList<>();

        // DB에 있는 모든 Task를 최신순으로 가져와 원하는 정보만을 추출
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

            HomeTaskDto homeTaskDto = HomeTaskDto.builder()
                    .taskId(task.getTaskId())
                    .title(task.getTitle())
                    .imageUrl(firstImage)
                    .taskStatus(enumToStringTaskStatus(task.getTaskStatus()))
                    .content(task.getContent())
                    .category(category)
                    .uploadDate(formattedUploadDate)
                    .requestFee(task.getRequestFee())
                    .build();

            taskList.add(homeTaskDto);
        };

        return taskList;
    }

    // 검색 기능 사용자 프로필 이미지, 사용한 키워드, List<HomeTaskDto> 갖고 있는 DTO(-> TaskSearchDto)  반환하기
    @Transactional
    public TaskSearchDto getKeywordSearch(Integer userId, String keyword){
        String profileImage = userRepository.findByUserId(userId).get().getProfileImage();

        List<HomeTaskDto> taskList = keywordSearchList(keyword);

        return TaskSearchDto.builder()
                .profileImage(profileImage)
                .keyword(keyword)
                .taskList(taskList)
                .build();
    }




    // 검색 기능 제목이나 내용에 해당 키워드가 포함되어 있는 task들을 최신순 정렬해 리스트로 반환
    @Transactional
    public List<HomeTaskDto> keywordSearchList(String keyword){
        return makeHomeTaskDtoList(taskRepository.findByTitleOrContentContainingOrderByUploadDateDesc(keyword));
    }

    // 카테고리별 검색
    @Transactional
    public List<TaskEntity> getFilteredTasks(Integer userId, boolean onlyMine,
                                             boolean school, boolean dormitory, boolean etc,
                                             boolean print, boolean food, boolean coverFor,
                                             boolean clean, boolean eventAssistant, boolean bug,
                                             boolean onlyYet) {

        List<TaskEntity> taskEntityList;

        if ((school || dormitory || etc) && (print || food || coverFor || clean || eventAssistant || bug)) {
            taskEntityList = taskRepository.findByLocationCategoryInAndDetailCategoryIn(
                    getSelectedLocationCategories(school, dormitory, etc),
                    getSelectedDetailCategories(print, food, coverFor, clean, eventAssistant, bug)
            );
        } else if (school || dormitory || etc) {
            taskEntityList = taskRepository.findByLocationCategoryIn(
                    getSelectedLocationCategories(school, dormitory, etc)
            );
        } else if (print || food || coverFor || clean || eventAssistant || bug) {
            taskEntityList = taskRepository.findByDetailCategoryIn(
                    getSelectedDetailCategories(print, food, coverFor, clean, eventAssistant, bug)
            );
        } else {
            return Collections.emptyList();
        }

        if (onlyYet) {
            taskEntityList = filterOnlyYetTasks(taskEntityList);
        }

        if (onlyMine) {
            taskEntityList = filterMyTasks(taskEntityList, userId);
        }

        // 최신순으로 정렬
        taskEntityList = taskEntityList.stream()
                .sorted(Comparator.comparing(TaskEntity::getUploadDate).reversed())
                .collect(Collectors.toList());

        return taskEntityList;
    }

    private List<TaskEntity> filterOnlyYetTasks(List<TaskEntity> tasks) { // 거래 전인 게시글만 포함
        return tasks.stream()
                .filter(task -> task.getTaskStatus() == TaskStatus.YET)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TaskEntity> filterMyTasks(List<TaskEntity> tasks, Integer userId) { // 내 게시글만 포함
        return tasks.stream()
                .filter(task -> task.getUser() != null && task.getUser().getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    private List<LocationCategory> getSelectedLocationCategories(boolean school, boolean dormitory, boolean etc) {
        // 선택된 위치 카테고리 반환
        List<LocationCategory> selectedCategories = new ArrayList<>();
        if (school) selectedCategories.add(LocationCategory.SCHOOL);
        if (dormitory) selectedCategories.add(LocationCategory.DORMITORY);
        if (etc) selectedCategories.add(LocationCategory.ETC);
        return selectedCategories;
    }

    private List<DetailCategory> getSelectedDetailCategories(boolean print, boolean food, boolean coverFor,
                                                             boolean clean, boolean eventAssistant, boolean bug) {
        // 선택된 디테일 카테고리 반환
        List<DetailCategory> selectedCategories = new ArrayList<>();
        if (print) selectedCategories.add(DetailCategory.PRINT);
        if (food) selectedCategories.add(DetailCategory.FOOD);
        if (coverFor) selectedCategories.add(DetailCategory.COVFR);
        if (clean) selectedCategories.add(DetailCategory.CLEAN);
        if (eventAssistant) selectedCategories.add(DetailCategory.EVTAST);
        if (bug) selectedCategories.add(DetailCategory.BUG);
        return selectedCategories;
    }

    // 카테고리 검색 결과 반환
    @Transactional
    public TaskCategorySearchDto getCatSearchTask(Integer userId, TaskCategoryDto taskCategoryDto, List<TaskEntity> taskList){
        String profileImage = userRepository.findByUserId(userId).get().getProfileImage();
        List<HomeTaskDto> homeTaskDtoList = makeHomeTaskDtoList(taskList);
        return TaskCategorySearchDto.builder()
                .profileImage(profileImage)
                .categoryDtoList(taskCategoryDto)
                .taskList(homeTaskDtoList)
                .build();
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
    public TaskStatus stringToEnumTaskStatus(String taskStatus){
        return switch (taskStatus){
            case "거래 전" -> TaskStatus.YET;
            case "예약 중" -> TaskStatus.RESERVATION;
            case "거래 완료" -> TaskStatus.DONE;
            default -> null;
        };
    }

}
