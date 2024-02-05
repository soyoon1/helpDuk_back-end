import React, { useState } from 'react';
import "../styles/RequestContent.css";

function LocationTimeSelector({ onLocationChange, onDetailCategoryChange, onTimeChange }) {
  const [locationCategory, setLocationCategory] = useState('');
  const [detailCategory, setDetailCategory] = useState('');
  const [taskTime, setTaskTime] = useState('');

  //위치 선택 핸들러
  const handleLocationChange = (e) => {
    setLocationCategory(e.target.value);
    onLocationChange(e.target.value);
    // console.log("LocationTimeSelector 위치: " , e.target.value);
  };

  // 상세 카테고리 선택 핸들러
  const handleDetailCategoryChange = (e) => {
    setDetailCategory(e.target.value);
    onDetailCategoryChange(e.target.value);
    // console.log("LocationTimeSelector 상세: " , e.target.value);
  };

  //시간 선택 핸들러
  const handleTimeChange = (e) => {
    setTaskTime(e.target.value);
    onTimeChange(e.target.value);
    // console.log("LocationTimeSelector 시간: " , e.target.value);
  };


  return (
    <div className='select-container'>
      <select value={locationCategory} onChange={handleLocationChange}>
        <option value="">위치</option>
        <option value="학교 안">학교 안</option>
        <option value="기숙사">기숙사</option>
        <option value="기타">기타</option>
      </select>
      <select value={detailCategory} onChange={handleDetailCategoryChange}>
        <option value="">상세 카테고리</option>
        <option value="프린트">프린트</option>
        <option value="음식">음식</option>
        <option value="알바 대타">알바 대타</option>
        <option value="청소">청소</option>
        <option value="행사 보조">행사 보조</option>
        <option value="벌레">벌레</option>
      </select>
      <input
        type="text"
        placeholder="시간"
        value={taskTime}
        onChange={handleTimeChange}
      />
    </div>
  );
}

export default LocationTimeSelector;