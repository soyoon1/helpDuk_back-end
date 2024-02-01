package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.dto.chat.ChatRoomListDto;
import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.entity.UserEntity;
import com.helpduk.helpDuk.repository.ChatRoomRepository;
import com.helpduk.helpDuk.repository.MessageRepository;
import com.helpduk.helpDuk.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private Map<String, ChatRoomListDto> chatRooms;


    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
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
    public ChatRoomEntity findById(String roomId) {
        ChatRoomEntity chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow();
        return chatRoom;
    }

    //채팅방 생성
    public ChatRoomEntity createRoom(Integer userId, Integer helperId) {

        UserEntity user = userRepository.findByUserId(userId).orElseThrow();
        UserEntity helper = userRepository.findByUserId(helperId).orElseThrow();

        // 이미 있는 채팅방인지 체크
        Optional<ChatRoomEntity> ch = chatRoomRepository.findByUserAndHelper(user, helper);

        if(ch.isPresent()){
            return ch.get();
        }

        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomId(UUID.randomUUID().toString())
                .user(user)
                .helper(helper)
                .build();

        chatRoomRepository.save(chatRoom);

        ChatRoomListDto list = new ChatRoomListDto(chatRoom, chatRoom.getHelper(), "");
        chatRooms.put(chatRoom.getRoomId(), list);
        return chatRoom;
    }
}
