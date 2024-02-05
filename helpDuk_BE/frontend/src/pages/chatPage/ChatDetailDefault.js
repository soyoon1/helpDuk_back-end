import "./ChatDetailDefault.css";
import ChatIcon from '../../assets/chatPage/chatIcon.png';

function ChatDetailDefault() { 
    return(
        <div className="chatdetaildefault">
            <div className="defaultScreen">
                <img src={ChatIcon} />
                <h5>다른 유저에게 메시지를 보내보세요!</h5>
            </div>
        </div>
    );
}

export default ChatDetailDefault;