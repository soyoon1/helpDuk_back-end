import "./ChatPage.css"; 
import ChatList from "./ChatList";
import ChatDetailDefault from "./ChatDetailDefault";

function ChatPage() { 
    return(
        <div className="chatpage">
            <ChatList />
            <ChatDetailDefault />
        </div>
    );
}

export default ChatPage;