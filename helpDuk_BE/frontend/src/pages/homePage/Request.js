import "./Request.css";
import sampleImg from "../../assets/homePage/sample.png";
import { useNavigate } from 'react-router-dom';

function Request({ requests }) {
    const navigate = useNavigate();

    const goToDetail = (taskId) => {
        navigate(`/detailPage/${taskId}`);
    }

    return (
        <div className="requests">
            {requests.map((request) => (
                <div className="request" key={request.taskId} onClick={() => goToDetail(request.taskId)}>
                    <div className="contents">
                        <div className="requestTitle">
                            <h2>{request.title}</h2>
                            <div className="isSolved">{request.taskStatus}</div>
                        </div>

                        <div className="detail">{request.content}</div>

                        <div className="category">{request.category}</div>
                    </div>
                    <div className="price"><h2>{request.requestFee}원</h2></div>
                    <div className="image"><img src={sampleImg} alt="Sample Image" /></div>
                </div>
            ))}
        </div>
    );
}

export default Request;
