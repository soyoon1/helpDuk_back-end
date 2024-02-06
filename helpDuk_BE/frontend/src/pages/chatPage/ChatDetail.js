import "./ChatDetail.css";
import Conversation from "./Conversation";
import {Link} from "react-router-dom";
import ProfileImg from "../../assets/chatPage/profileImg.png";
import { useChatMock } from '../../components/MockChat';
import { useParams } from 'react-router-dom';

function ChatDetail({chat}) { 
    const { mockChat} = useChatMock();
    const { roomId } = useParams();

    const chatRoomIndex = mockChat.findIndex(chatroom => chatroom.roomId === roomId);
    const chatRoom = mockChat[chatRoomIndex];

    return(
        <div className="chatdetail">
            <div className="header">
                <div className="chatDetailProfile">
                    <img src={ProfileImg} />
                    <h5>{chatRoom.helper.nickName}</h5>
                </div>
                    <Link to={"/writeReview"}>
                        <button className="requestFormButton">거래완료</button>
                    </Link>
            </div>

            <div className="contents">
                <div className="conversation">
                    <Conversation />
                </div>
                <div className="sendBar">
                    <input className="writeMessage" />
                    <button className="sendButton">전송</button>
                </div>
            </div>
        </div>
    );
}

export default ChatDetail;