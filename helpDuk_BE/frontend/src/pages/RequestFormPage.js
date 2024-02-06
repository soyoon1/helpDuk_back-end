import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import FileUploader from '../components/FileUploader';
import PaymentBox from '../components/PaymentBox';
import "../styles/RequestFormPage.css";
import axios from 'axios';

function RequestFormPage() {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [files, setFiles] = useState([]);
  const [formData, setFormData] = useState({});

  // 위치 선택 핸들러
  const handleLocationChange = (location) => {
    setFormData(prevState => ({ ...prevState, locationCategory: location }));
  };

  // 상세 카테고리 선택 핸들러
  const handleDetailCategoryChange = (detail) => {
    setFormData(prevState => ({ ...prevState, detailCategory: detail }));
  };

  // 시간 선택 핸들러
  const handleTimeChange = (time) => {
    setFormData(prevState => ({ ...prevState, taskTime: time }));
  };

  const handleFileChange = (files) => {
    setFiles(files);
  };


  const handlePaymentChange = (name, value) => {
    if (name === 'requestFee') {
      setFormData(prevState => ({
        ...prevState,
        requestFee: value
      }));
    }
    if (name === 'requestFeeMethod') {
      setFormData(prevState => ({
        ...prevState,
        requestFeeMethod: value
      }));
    if (name === 'taskFee') {
      setFormData(prevState => ({
        ...prevState,
        taskFee: value
      }));
    }
    if (name === 'taskFeeMethod') {
      setFormData(prevState => ({
        ...prevState,
        taskFeeMethod: value
      }));
    }
  };

  const handleRegister = async () => {
    if (title && content && files.length > 0) {

      console.log('title:', title);
      console.log('content:', content);
      console.log('files:', files);


      console.log('formData:', formData);


      alert("의뢰글이 등록되었습니다.");
      navigate('/homepage');
    } else if (!title || !content) {
      alert("의뢰글 작성을 완성해주세요.");
    } else {
      alert("이미지를 삽입해주세요.");
    }
  };


   const handleRegister = async () => {
     if (title && content && files.length > 0) {
       const formDataToSend = new FormData();
       formDataToSend.append('title', title);
       formDataToSend.append('content', content);
       formDataToSend.append('locationCategory', formData.locationCategory);
       formDataToSend.append('detailCategory', formData.detailCategory);
       files.forEach((file) => formDataToSend.append('files', file));
       formDataToSend.append('taskTime', formData.taskTime);
       formDataToSend.append('requestFee', formData.requestFee);
       formDataToSend.append('requestFeeMethod', formData.requestFeeMethod);
       formDataToSend.append('taskFee',formData.taskFee);
       formDataToSend.append('taskFeeMethod', formData.taskFeeMethod);

       try {
         const response = await axios.post('/api/task', formDataToSend, {
           headers: {
             'X-AUTH-TOKEN': "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA3MDk3NDk5LCJleHAiOjE3MDcxMDEwOTl9.yQLSWD_TUycrqY_dAe0mESfQO_fZ6nNENUHxQQ0qVM4", // 여기에 토큰 값을 넣어주세요
             "Content-Type": "multipart/form-data",
           },
         });
         console.log('응답:', response.data);
         alert("의뢰글이 등록되었습니다.");
         navigate('/homepage');
       } catch (error) {
         console.error('에러:', error);
       }
     } else if (!title || !content) {
       alert("의뢰글 작성을 완성해주세요.");
     } else {
       alert("이미지를 삽입해주세요.");
     }
   };


  return (
    <div className="page-container">
        <div className="request-form-container">
        <FileUploader onFileChange={handleFileChange}
          onLocationChange={handleLocationChange}
          onDetailCategoryChange={handleDetailCategoryChange}
          onTimeChange={handleTimeChange} />
            <div>
                <button className="custom-button" onClick={handleRegister}>등록하기</button>
                <div className="request-letter">
                    <div className="title">
                        <input type="text" placeholder="제목을 작성하세요." value={title} onChange={(e) => setTitle(e.target.value)} />
                    </div>
                    <div className="content">
                        <textarea placeholder="의뢰 내용을 작성하세요." value={content} onChange={(e) => setContent(e.target.value)} />
                    </div>
                </div>
            </div>
            <br />
        </div>
        <PaymentBox onPaymentChange={handlePaymentChange} />
    </div>
);
}

export default RequestFormPage;