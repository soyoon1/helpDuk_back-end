import "./WriteReview.css"
import React from "react"
import { useState } from "react";
import { useNavigate  } from 'react-router-dom';
import reviewLog from "../../assets/image/부름부릉얼굴.png"
import Good from "../../assets/image/좋아요.png"
import Bad from "../../assets/image/별로예요.png"
import Great from "../../assets/image/최고예요.png"


function WriteReview () {

    const [review, setReview] = useState('');

    const reviewChange = (e) => {
        setReview(e.target.value);
    }

    const navigate = useNavigate();

    const [reviewScore, setReviewScore] = useState("");
    const handleClickScore = (e) => {
        setReviewScore(e.target.value);
    }

    const cancelClick = () => {
    const confirmcancel = window.confirm("후기 작성을 취소하시겠습니까?")

    if (confirmcancel){
        navigate('/mypage/myRequests');
    } else {
    }
    
    };

    return (
        <div className="writeReview"  style={{padding: "20px"}}>
            <div className="reviewIntro">
                <img className="reviewlog" alt="reviewlog"src={reviewLog} />
                <h2>헬퍼와의 거래는 만족스러우셨나요? 사용자님의 후기를 남겨주세요!</h2>
            </div>
            <div className="reviewImg">
                <label>
                    <input
                    type="radio"
                    className="radioButton"
                    value="Bad"
                    checked={reviewScore==="Bad"}
                    onChange={handleClickScore}
                    />
                    <div>
                        <img className="badImg" alt="bad" src={Bad} />
                        <h2>별로예요!</h2>
                    </div>
                </label>
                <label>
                    <input
                    type="radio"
                    className="radioButton"
                    value="Good"
                    checked={reviewScore==="Good"}
                    onChange={handleClickScore}
                    />
                    <div>
                    <img className="goodImg" alt="good" src={Good} />
                    <h2>좋아요!</h2></div>
                </label>
                <label>
                    <input
                    type="radio"
                    className="radioButton"
                    value="Great"
                    checked={reviewScore==="Great"}
                    onChange={handleClickScore}
                    />
                    <div>
                    <img className="greatImg" alt="great" src={Great} />
                    <h2>최고예요!</h2></div>
                </label>
            </div>
            <div className="writePage">
                <form onSubmit={reviewChange}>
                    <textarea 
                        value={review}
                        onChange={reviewChange}
                        cols="100"
                        rows="7"
                        placeholder="의뢰 후기를 작성해주세요!"
                    />
                    <div className="buttonStyle">
                        <button onClick={cancelClick} className="cancelButton" style={{backgroundColor: "white"}}>취소하기</button>
                        <button type="submit" style={{backgroundColor: "#80BCBD"}}>등록하기</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default WriteReview;