import { useUser } from '../../components/UserContext';
import heart from '../../assets/image/하트.png';
import "./HelperList.css"

function HelperList() {
    const { userImage, nickname } = useUser();

    return (
        <div style={{padding: "20px"}}>
            <h1 className="editTitle" style={{paddingBottom: "30px"}}>관심 목록</h1>
            <div className='helperList'>
                <img alt="heart" src={heart} />
                <img className="userImg" alt="user" src={userImage} />
                <h1>{nickname}</h1>
            </div>
        </div>
    )
}

export default HelperList;