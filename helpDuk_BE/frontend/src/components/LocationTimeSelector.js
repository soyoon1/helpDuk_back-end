import React, { useState } from 'react';
import "../styles/RequestContent.css";

function LocationTimeSelector() {
  // 위치와 시간을 선택할 상태
  const [location, setLocation] = useState('');
  const [time, setTime] = useState('');

  // 위치 선택 핸들러
  const handleLocationChange = (e) => {
    setLocation(e.target.value);
  };

  // 시간 선택 핸들러
  const handleTimeChange = (e) => {
    setTime(e.target.value);
  };

  return (
    <div className='select-container'>
      <select value={location} onChange={handleLocationChange}>
        <option value="">위치</option>
        <option value="학교 안">학교 안</option>
        <option value="학교 밖">기숙사</option>
        <option value="기타">기타</option>
      </select>
      <input
        type="text"
        placeholder="시간"
        value={time}
        onChange={handleTimeChange}
      />
    </div>
  );
}

export default LocationTimeSelector;