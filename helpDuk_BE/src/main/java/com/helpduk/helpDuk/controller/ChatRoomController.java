package com.helpduk.helpDuk.controller;

import com.helpduk.helpDuk.entity.ChatRoomEntity;
import com.helpduk.helpDuk.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms/{userId}")
    @ResponseBody
    public List<ChatRoomEntity> room(@PathVariable Integer userId) {
        return chatService.findAllRoom(userId);
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoomEntity createRoom(@RequestParam String name, @RequestParam Integer userId) {
        return chatService.createRoom(name, userId);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomEntity roomInfo(@PathVariable String roomId) {

        ChatRoomEntity chatRoom = chatService.findById(roomId);

        return chatRoom;
    }
}
