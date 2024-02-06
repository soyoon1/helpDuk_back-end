import React, { useState } from 'react';
import "../styles/Fee.css";

function RequestFee({ onRequestFeeChange }) {
  const [requestFee, setRequestFee] = useState(0);
  const [requestFeeMethod, setRequestFeeMethod] = useState('');

  const handlerequestFeeChange = (e) => {
    const value = e.target.value;
    setRequestFee(value);
    onRequestFeeChange('requestFee', value)
  };

  const handlerequestFeeMethodChange = (e) => {
    const value = e.target.value;
    setRequestFeeMethod(value);
    onRequestFeeChange('requestFeeMethod', value);
  };

  return (
    <div className='fee'>
        <h3>의뢰 비용</h3>
        <p>0원 심부름은 해당없음을 눌러주세요.</p>
        <input type="text" value={requestFee} onChange={handlerequestFeeChange} placeholder="0원" className='custom-money' />
        <select value={requestFeeMethod} onChange={handlerequestFeeMethodChange} className="custom-select">
            <option value="">지불방법</option>
            <option value="계좌 이체">계좌 이체</option>
            <option value="현금">현금</option>
            <option value="기타">기타</option>
            <option value="해당 없음">해당 없음</option>
        </select>
    </div>
  );
}

export default RequestFee;