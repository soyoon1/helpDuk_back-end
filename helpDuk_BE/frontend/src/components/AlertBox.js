import React from 'react';
import { useNavigate } from 'react-router-dom'; 
import "../styles/AlertBox.css";
import alertIcon from "../assets/image/alertIcon.png";

function AlertBox({ timestamp }) {
    const navigate = useNavigate();

    // 알림 클릭 핸들러
    const handleAlertClick =() => {
        // 클릭 시 의뢰 후기 페이지로 이동
        navigate("/myPage/ReviewList");
    }
    return (
        <div className="alert-item" onClick={handleAlertClick}>
            <div className="content-wrapper">
                <img src={alertIcon} alt="Alert Icon" className="alert-icon" />
                <span className='text'>리뷰가 등록되었어요</span>
                <span className='timestamp'>{timestamp}</span>
            </div>
        </div>
    );
}

export default AlertBox;