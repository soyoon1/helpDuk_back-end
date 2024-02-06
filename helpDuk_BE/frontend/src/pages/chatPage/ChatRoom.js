import "./ChatRoom.css";
import ProfileImg from "../../assets/chatPage/profileImg.png";
import { useNavigate } from 'react-router-dom';

function ChatRoom({chat}) {
    const navigate = useNavigate();

    const goToChatRoom = (roomId) => {
        navigate(`/chatPage/${roomId}`);
    }

    return(
        <div className="chatrooms"> 
                {chat.map((chat) => (
                    <div className="chatroom">
                            <button className="chatroomButton" key={chat.roomId} onClick={() => goToChatRoom(chat.roomId)}>
                                <div className="chatprofile">
                                    <img src={ProfileImg} />
                                </div>
                                <div className="chatroomInfo">
                                    <div className="userName">
                                        <h3>{chat.helper.nickName}</h3>
                                    </div>
                                    <div className="recentChat">
                                        <p>{chat.lastContent}</p>
                                    </div>
                                </div>
                            </button>
                    </div>
                ))} 
        </div>
    );
}

export default ChatRoom;