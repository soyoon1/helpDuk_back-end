import { BrowserRouter, Route, Routes } from "react-router-dom"
import './App.css';
import Nav from "./components/Nav";
import Footer from "./components/Footer";
import Mypage from "./pages/myPage/Mypage"; 
import ProfileEdit from "./pages/myPage/ProfileEdit";
import MyRequests from "./pages/myPage/MyRequests";
import HelpedRequests from "./pages/myPage/HelpedRequests";
import HelperList from "./pages/myPage/HelperList";
import ReviewList from "./pages/myPage/ReviewList";
import WriteReview from "./pages/myPage/WriteReview";
import HomePage from "./pages/homePage/HomePage";
import ChatPage from "./pages/chatPage/ChatPage";
import ChatPageDetail from "./pages/chatPage/ChatPageDetail";
import DetailPage from "./pages/detailPage/DetailPage"
import Login from "./pages/LoginPage";
import Signup from "./pages/SignupPage"
import Alert from "./pages/AlertPage"
import RequestForm from "./pages/RequestFormPage"
import { UserProvider } from './components/UserContext';
import SampleImg from "./assets/homePage/sample.png";
import { useState } from "react"
import { MockProvider } from './components/MockContext';
import { ChatMockProvider } from './components/MockChat';
import { ConversationMockProvider } from './components/MockConversation';

function App() {

  return (
    <UserProvider>
      <MockProvider>
        <ChatMockProvider>
          <ConversationMockProvider>
      <BrowserRouter>
        <div className="App">
          <Nav />
          <div className="main">
            <Routes>
              <Route path={"/homepage"} element={<HomePage />} />
              <Route path={"/login"} element={<Login />} />
              <Route path={"/signup"} element={<Signup />} />
              <Route path={"/alert"} element={<Alert />} />
              <Route path={"/mypage"} element={<Mypage />} />
              <Route path={"/requestForm"} element={<RequestForm />} />
              <Route path={"/detailPage/:taskId"} element={<DetailPage />} />
              <Route path={"/mypage/profileEdit"} element={<ProfileEdit />} />
              <Route path={"/mypage/myRequests"} element={<MyRequests />} />
              <Route path={"/mypage/helpedRequests"} element={<HelpedRequests />} />
              <Route path={"/mypage/helperList"} element={<HelperList />} />
              <Route path={"/mypage/reviewList"} element={<ReviewList />} />
              <Route path={"/writeReview"} element={<WriteReview />} />
              <Route path={"/ChatPage/:roomId"} element={<ChatPageDetail />} />
              <Route path={"/chatpage"} element={<ChatPage />} />
            </Routes>
          </div>
          <Footer />   
        </div>
      </BrowserRouter>
      </ConversationMockProvider>
      </ChatMockProvider>
      </MockProvider>
    </UserProvider>
  );
}

export default App;
