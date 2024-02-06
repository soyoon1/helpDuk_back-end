import { useUser } from '../../components/UserContext';
import "./ReviewDetail.css";

function ReviewDetail() {
    const { userImage, nickname } = useUser();

    return (
        <div className="reviewDetail"  style={{padding: "20px"}}>
            <div className='detailList'>
                <div className="reviewUser">
                    <img className="userImg" alt="user" src={userImage} />
                    <h3>{nickname}</h3>
                </div>
                <small style={{fontSize: "1.1em"}}>시간 넉넉하게 와주셨엇요. 감사합니다!</small>
            </div>
        </div>
    )
}

export default ReviewDetail;