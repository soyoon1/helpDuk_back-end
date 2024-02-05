import "./MyRequests.css"
import RequestsPhoto from "../../assets/image/아마스빈.png";
import { Link } from "react-router-dom";
import { useMock } from '../../components/MockContext';
import { useNavigate } from 'react-router-dom';


function MyRequests() {
    const { mockDate } = useMock();
    const navigate = useNavigate();

    const Goto = (taskId) => {
        navigate(`/detailPage/${taskId}`);
    }

    const stopPropagation = (e) => {
        e.stopPropagation(); // 이벤트 버블링 방지
    };

    return (
        <div className="myRequests"  style={{padding: "20px"}}>
            <h1 className="editTitle">의뢰 목록</h1>
            {mockDate.map((request) => (
            <div key={request.taskId} className="myList" onClick={() => Goto(request.taskId)}>
                <img className="requestsPhoto" alt="requestsPhoto" src={RequestsPhoto} />
                <div className="ListInfo">
                    <h3>{request.title}</h3>
                    <h4 style={{color: "#757575"}}>{request.uploadDate}까지</h4>
                    <div className="dealStatus">
                        <h4 className="requestsState">{request.taskStatus}</h4>
                        <h3>{request.price || request.requestFee}원</h3>
                    </div>
                    <Link to={"../writeReview"} onClick={stopPropagation}><h3 className="reviewwriterButton">후기 작성</h3></Link>
                </div>
            </div>
            ))}
        </div>
    )
}

export default MyRequests;