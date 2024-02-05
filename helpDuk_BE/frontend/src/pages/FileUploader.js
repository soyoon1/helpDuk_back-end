import React, { useState } from 'react';
import "../styles/RequestContent.css";
import camera from "../assets/image/camera.png";
import "./LocationTimeSelector.js";
import LocationTimeSelector from './LocationTimeSelector.js';
// import axios from 'axios';

function FileUploader({ onFileChange, onLocationChange, onDetailCategoryChange, onTimeChange }) {
    const [errorMessage, setErrorMessage] = useState('');

    // 파일 선택 핸들러
    const handleFileChange = (e) => {
        const selectedFiles = e.target.files;
        const validFiles = [];

        for (let i = 0; i < selectedFiles.length; i++) {
            const file = selectedFiles[i];
            if (file && file.type.includes('image')) {
                validFiles.push(file);
            }
        }

        if (validFiles.length > 0) {
            setErrorMessage('');
            onFileChange(validFiles); // 배열로 전달
        } else {
            setErrorMessage('이미지 파일만 선택해주세요.');
            onFileChange([]); // 빈 배열 전달
        }
    };

    console.log("FileUploader 위치: " , e.target.value);


    return (
        <div className="vertical-align-container">
              <LocationTimeSelector
                onLocationChange={onLocationChange}
                onDetailCategoryChange={onDetailCategoryChange}
                onTimeChange={onTimeChange}
            />
            <br />
            <br />
            <div className='file-upload'>
                <div className="upload-wrapper">
                    <img src={camera} alt="camera" className="camera" />
                    <input className="submit" type="file" accept="image/png, image/jpeg, image/gif" onChange={handleFileChange} />
                </div>
            </div>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        </div>
    );
}

export default FileUploader;