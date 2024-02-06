import React, { useState, createContext, useContext } from 'react';

/*채팅방 목 데이터*/
const mockChatRoom = [
    {
        roomId: "a85df236-61d9-4a79-ab0d-b77dde8c0282",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 3,
            nickName: "user3",
            profileImage: ""
        },
        lastContent: "1번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1c",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user2",
            profileImage: ""
        },
        lastContent: "2번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1d",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user4",
            profileImage: ""
        },
        lastContent: "3번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1e",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user5",
            profileImage: ""
        },
        lastContent: "4번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1f",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user6",
            profileImage: ""
        },
        lastContent: "5번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1g",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user7",
            profileImage: ""
        },
        lastContent: "6번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1h",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user8",
            profileImage: ""
        },
        lastContent: "7번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1i",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user9",
            profileImage: ""
        },
        lastContent: "8번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1j",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user10",
            profileImage: ""
        },
        lastContent: "9번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1k",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user11",
            profileImage: ""
        },
        lastContent: "10번째 채팅방 최근 대화 내용"
    },
    {
        roomId: "fab0a0b1-d36d-472d-b4ec-269b6f972e1l",
        taskId: null,
        userId: 1,
        helper: {
            helperId: 2,
            nickName: "user12",
            profileImage: ""
        },
        lastContent: "11번째 채팅방 최근 대화 내용"
    }
]

const ChatMockContext = createContext();

export const useChatMock = () => useContext(ChatMockContext);

export const ChatMockProvider = ({children}) => {
    const [mockChat, setMockChat] = useState(mockChatRoom);

    return (
        <ChatMockContext.Provider value={{mockChat, setMockChat}}>
            {children}
        </ChatMockContext.Provider>
    );
};