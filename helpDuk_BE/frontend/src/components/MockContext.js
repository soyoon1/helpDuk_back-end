import React, { useState, createContext, useContext } from 'react';

const mockRequest = [
    {
        nickName: "도담",
        temperature: 37.96,

        taskId: 0,
        title: "프린트 해주실 분",
        detail: "차관에서 만나요",
        taskStatus: "거래 완료",
        category: "프린트",
        content: "학교 앞으로 와주세요.",
        uploadDate: "2024-01-30 15:28:41",
        requestFee: 3000,
        requestFeeMethod: "현금",
        taskFee: 300,
        taskFeeMethod: "심부름  계좌 이체",
        taskTime: "20분 후 까지",
        chattingCount: 2,
        isItMine: true
    },
    {
        nickName: "미뇽",
        temperature: 37.96,

        taskId: 1,
        title: "커피 사다주실 분",
        detail: "도서관에서 만나요",
        taskStatus: "거래 전",
        category: "음식",
        content: "빠르신 분만!",
        uploadDate: "2024-01-30 15:28:41",
        requestFee: 2000,

        requestFeeMethod: "계좌 이체",
        taskFee: 5000,
        taskFeeMethod: "심부름 전 계좌 이체",
        taskTime: "30분 후 까지",
        chattingCount: 3,
        isItMine: false
    },
    {
        nickName: "니니",
        temperature: 90.96,

        taskId: 3,
        title: "같이 밥 먹으실 분",
        detail: "학관에서 만나요",
        taskStatus: "거래 완료",
        category: "학교 안",
        content: "학교 뒤으로 와주세요.",
        uploadDate: "2024-01-30 15:28:41",
        requestFee: 1000,

        requestFeeMethod: "계좌 이체",
        taskFee: 0,
        taskFeeMethod: "심부름 전 계좌 이체",
        taskTime: "40분 후 까지",
        chattingCount: 5,
        isItMine: true
    }
  ]

const MockContext = createContext();

export const useMock = () => useContext(MockContext);

export const MockProvider = ({children}) => {
    const [mockDate, setMockDate] = useState(mockRequest);

    return (
        <MockContext.Provider value={{mockDate, setMockDate}}>
            {children}
        </MockContext.Provider>
    )
}