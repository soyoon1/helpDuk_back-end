import React, { useState } from 'react';
import "../styles/RequestContent.css";

function RequestLetter({ onRegister }) {
  // 제목 입력 핸들러
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  // 제목 입력 핸들러
  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  // 내용 입력 핸들러
  const handleContentChange = (e) => {
    setContent(e.target.value);
  };

  // 등록하기 버튼 클릭 시 실행되는 함수
  const handleRegister = () => {
    if (title && content) {
      onRegister(); // 부모 컴포넌트에 등록 완료를 알림
    } else {
      alert("의뢰글 작성을 완성해주세요.");
    }
  };

  return (
    <div>
      <button className="custom-button" onClick={handleRegister}>등록하기</button>
      <div className="request-letter">
        <div className="title">
          <input type="text" placeholder="제목을 작성하세요." value={title} onChange={handleTitleChange} />
        </div>
        <div className="content">
          <textarea placeholder="의뢰 내용을 작성하세요." value={content} onChange={handleContentChange} />
        </div>
      </div>
    </div>
  );
}

export default RequestLetter;