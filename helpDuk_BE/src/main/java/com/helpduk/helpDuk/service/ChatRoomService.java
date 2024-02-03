package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.dto.chat.ChatRoomInfoDto;
import com.helpduk.helpDuk.base.dto.chat.ChatRoomListDto;
import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.TaskEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
import com.helpduk.helpDuk.repository.MessageRepository;
import com.helpduk.helpDuk.repository.TaskRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final TaskRepository taskRepository;
    private Map<String, ChatRoomListDto> chatRooms;


    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();

    }

    //채팅방 불러오기
    public List<ChatRoomListDto> findAllRoom(Integer userId) {

        // 회원의 채팅방만 가져오기
        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        List<ChatRoomEntity> userRoom = chatRoomRepository.findByUser(user);

        for (ChatRoomEntity chatRoom : userRoom) {
            String lastContent = messageRepository.findTopContentByRoomIdOrderedByMessageIdDesc(chatRoom.getRoomId());

            if(lastContent==null) lastContent="";

            ChatRoomListDto list = new ChatRoomListDto(chatRoom, chatRoom.getHelper(), lastContent);
            chatRooms.put(chatRoom.getRoomId(), list);
        }

        //채팅방 최근 생성 순으로 반환
        List<ChatRoomListDto> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoomInfoDto findById(Integer userId, String roomId) {

        userRepository.findById(userId).orElseThrow();
        ChatRoomEntity chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow();

        ChatRoomInfoDto chatRoomInfoDto = new ChatRoomInfoDto(chatRoom);
        return chatRoomInfoDto;
    }

    //채팅방 생성
    public ChatRoomInfoDto createRoom(Integer userId, Integer helperId, Integer taskId) {

        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        UserEntity helper = userRepository.findByUserId(helperId).orElseThrow();
        TaskEntity task = taskRepository.findByTaskId(taskId).orElseThrow();

        ChatRoomInfoDto chatRoomInfoDto;

        // 이미 있는 채팅방인지 체크
        Optional<ChatRoomEntity> ch = chatRoomRepository.findByUserAndHelper(user, helper);

        if(ch.isPresent()){
            chatRoomInfoDto = new ChatRoomInfoDto(ch.get());
            return chatRoomInfoDto;
        }

        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomId(UUID.randomUUID().toString())
                .user(user)
                .helper(helper)
                .task(task)
                .build();

        chatRoomRepository.save(chatRoom);

        ChatRoomListDto list = new ChatRoomListDto(chatRoom, chatRoom.getHelper(), "");
        chatRooms.put(chatRoom.getRoomId(), list);
        chatRoomInfoDto = new ChatRoomInfoDto(chatRoom);
        return chatRoomInfoDto;
    }
}
