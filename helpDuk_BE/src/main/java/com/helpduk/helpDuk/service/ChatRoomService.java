package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
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


    private Map<String, ChatRoomEntity> chatRooms;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoomEntity> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoomEntity> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoomEntity findById(String roomId) {
//        return chatRooms.get(roomId);
        ChatRoomEntity chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow();
        return chatRoom;
    }

    //채팅방 생성
    public ChatRoomEntity createRoom(String name) {
        // ChatRoomEntity chatRoom = ChatRoomEntity.create(name);

        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(name)
                .build();

        chatRoomRepository.save(chatRoom);

        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
