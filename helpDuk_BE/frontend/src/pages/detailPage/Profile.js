import "./Profile.css";
import { useUser } from '../../components/UserContext';
import Temperature from "../../components/Temperature";
import { useMock } from '../../components/MockContext';
import { useParams } from 'react-router-dom';

function Profile() {
    const { userImage } = useUser();

    const { mockDate, setMockDate } = useMock();
    const { taskId } = useParams();

    const requestIndex = mockDate.findIndex(request => request.taskId === +taskId);
    const request = mockDate[requestIndex];

    return(
        <div className="profile">
            <div className="profile-contents">
                <img src={userImage} />
                <h4>{request.nickName}</h4>
                <Temperature userTemperature={request.temperature} />
            </div>
        </div>
    );
}

export default Profile;