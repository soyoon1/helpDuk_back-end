import React, { useState } from 'react';
import "../styles/Nav.css";
import logImage from '../assets/image/부름부릉.jpg';
import alarmImage from '../assets/image/알림.jpg';
import chatImage from '../assets/image/채팅.jpg';
import {Link} from "react-router-dom"
import { useUser } from './UserContext';

function Nav () {
    const [isLogin, setIsLogin] = useState(false);
    const { userImage } = useUser();

    return (
        <div className="navbar">
            <div>
                <Link to={"./"}> 
                    <img className="log" alt="buleumbuleung" src={logImage} />
                </Link>
            </div>
            {isLogin ? (
            // 로그인 전
            <div>
                <Link to={""}>
                    <strong>로그인/회원가입</strong>
                </Link>
            </div> ) : (
            // 로그인 후
            <div className="navIcon">
                <Link to={"./chatpage"}>
                    <img className="navchat" alt="chatRome" src={chatImage} />
                </Link>
                <Link to={"./alert"}>
                    <img className="navalarm" alt="alarm" src={alarmImage} />
                </Link>
                <Link to={"./mypage"}>
                    <img className="navprofile" alt="profile" src={userImage} />
                </Link>
            </div> )}
        </div>
    )
}

export default Nav;