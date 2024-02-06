import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import "../styles/LoginPage.css";
import cloud_black from "../assets/image/cloud_black.png";

/* 모의 데이터
const mockUsers = [
  { email: 'user1@example.com', password: 'pw1' },
  { email: 'user2@example.com', password: 'pw2' },
];
*/

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); // useNavigate를 사용하여 페이지 이동을 처리합니다.

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  }

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value)
  }

  const onSubmit = (event) => {
    event.preventDefault();
    // 이메일과 비밀번호를 확인하는 로직을 여기에 구현?
    const isAuthenticated = true; // 이 부분을 실제 로그인 로직으로 대체할 것

    if (isAuthenticated) {
      // 로그인이 성공했을 때만 홈페이지로 이동
      navigate('/homepage');
    } else {
      // 로그인이 실패했을 때 페이지 새로고침
      window.location.reload();
      alert('이메일 또는 비밀번호가 맞지 않습니다.');
    }

    /* 모의데이터로 test
    // 입력된 이메일과 비밀번호가 모의 데이터와 일치하는지 확인합니다.
    const user = mockUsers.find(u => u.email === email && u.password === password);
    if (user) {
      // 올바른 경우에는 홈페이지로 이동합니다.
      navigate('/homepage');
    } else {
      // 잘못된 경우에는 알림창을 띄웁니다.
      alert('이메일 또는 비밀번호가 맞지 않습니다.');
    }
    */
  }

  return (
    <div className="login">
      <div className="image-container">
        <img src={cloud_black} alt="cloud_black" className="cloud-logo" />
      </div>
      <form onSubmit={onSubmit}>
        <div>
          <input name="email" type="email" value={email} onChange={onEmailHandler} placeholder="user@duksung.ac.kr" />
        </div>
        <div>
          <input name="password" type="password" value={password} onChange={onPasswordHandler} placeholder="비밀번호" />
        </div>
        <div>
          <button className="login-button" type="submit">로그인</button>
        </div>
        <div>
          <Link to="/signup">
            <button className="signup-button">회원가입</button>
          </Link>
        </div>
      </form>
    </div>
  );
};

export default LoginPage;
