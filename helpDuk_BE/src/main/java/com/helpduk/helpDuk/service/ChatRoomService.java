package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
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
    private Map<String, ChatRoomEntity> chatRooms;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();

    }

    //채팅방 불러오기
    public List<ChatRoomEntity> findAllRoom(Integer userId) {

        // 회원의 채팅방만 가져오기
        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        List<ChatRoomEntity> userRoom = chatRoomRepository.findByUser(user);

        for (ChatRoomEntity chatRoom : userRoom) {
            chatRooms.put(chatRoom.getRoomId(), chatRoom);
        }

        //채팅방 최근 생성 순으로 반환
        List<ChatRoomEntity> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoomEntity findById(String roomId) {
        ChatRoomEntity chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow();
        return chatRoom;
    }

    //채팅방 생성
    public ChatRoomEntity createRoom(String name, Integer userId, Integer helperId) {

        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        UserEntity helper = userRepository.findByUserId(helperId).orElseThrow();

        // 이미 있는 채팅방인지 체크
        Optional<ChatRoomEntity> ch = chatRoomRepository.findByUserAndHelper(user, helper);

        if(ch.isPresent()){
            return ch.get();
        }

        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(name)
                .user(user)
                .helper(helper)
                .build();

        chatRoomRepository.save(chatRoom);

        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
