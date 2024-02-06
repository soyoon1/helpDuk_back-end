import { useLocation } from 'react-router-dom';
import ReviewDetail from './ReviewDetail';
import "./ReviewList.css";

function ReviewList() {
    const location = useLocation();
    const reviewNum = location.state ? location.state.reviewNum : 0;
    console.log({reviewNum})

    return (
        <div className="reviewList"  style={{padding: "20px"}}>
            <h1 className="editTitle">의뢰 후기 상세</h1>
            <h2 style={{paddingTop: "50px"}}>후기 {reviewNum}개</h2>
            <div style={{marginLeft: "110px"}}>
                    <ReviewDetail />
                    <ReviewDetail />
                    <ReviewDetail />
                    <ReviewDetail />
            </div>
        </div>
    )
}

export default ReviewList;