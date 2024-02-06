import "./ChatPage.css"; 
import ChatList from "./ChatList";
import ChatDetail from "./ChatDetail";

function ChatPageDetail() { 
    return(
        <div className="chatpage">
            <ChatList />
            <ChatDetail />
        </div>
    );
}

export default ChatPageDetail;