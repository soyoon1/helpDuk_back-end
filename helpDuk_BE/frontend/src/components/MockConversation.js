import React, { useState, createContext, useContext } from 'react';

/*채팅 대화내용 목 데이터*/
const mockConversationData = [
    {
        "messageId": 1,
        "senderId": {
            "userId": 1,
            "userEmail": "user2",
            "password": "1234",
            "nickName": "user2",
            "profileImage": "",
            "temperature": 35.6
        },
        "content": "안녕하세요",
        "sendTime": null
    },
    {
        "messageId": 2,
        "senderId": {
            "userId": 1,
            "userEmail": "user2",
            "password": "1234",
            "nickName": "user2",
            "profileImage": "",
            "temperature": 35.6
        },
        "content": "안녕하세요",
        "sendTime": null
    }
]

const ConversationMockContext = createContext();

export const useConversationMock = () => useContext(ConversationMockContext);

export const ConversationMockProvider = ({children}) => {
    const [mockConversation, setMockConversation] = useState(mockConversationData);

    return (
        <ConversationMockContext.Provider value={{mockConversation, setMockConversation}}>
            {children}
        </ConversationMockContext.Provider>
    )
}