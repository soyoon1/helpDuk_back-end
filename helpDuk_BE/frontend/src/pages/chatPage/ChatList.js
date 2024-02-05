import "./ChatList.css";
import ChatRoom from "./ChatRoom";
import { useChatMock } from '../../components/MockChat';

function ChatList() { 
    const { mockChat} = useChatMock();

    return(
        <div className="chatlist">
            <div className="chatListNav">
                <h3>Message</h3>
            </div>
            <ChatRoom chat={mockChat}/>
        </div>
    );
}

export default ChatList;