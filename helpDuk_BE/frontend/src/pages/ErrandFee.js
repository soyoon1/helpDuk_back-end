import React, { useState } from 'react';
import "../styles/Fee.css";

function ErrandFee({ onErrandFeeChange }) {
  const [taskFee, setTaskFee] = useState(0);
  const [taskFeeMethod, setTaskFeeMethod] = useState('');

  const handletaskFeeChange = (e) => {
    const value = e.target.value;
    setTaskFee(value);
    onErrandFeeChange('taskFee', value);
  };

  const handletaskFeeMethodChange = (e) => {
    const value = e.target.value;
    setTaskFeeMethod(value);
    onErrandFeeChange('taskFeeMethod', value); // 값 변경 시 콜백 함수 호출
  };

  return (
    <div className='fee'>
        <h3>심부름 비용</h3>
        <p>심부름 비용은 되도록 미리 전해주세요.</p>
        <input type="text" value={taskFee} onChange={handletaskFeeChange} placeholder="0원" className='custom-money' />
        <select value={taskFeeMethod} onChange={handletaskFeeMethodChange} className="custom-select">
            <option value="">지불방법</option>
            <option value="심부름 전 계좌 이체">심부름 전 계좌 이체</option>
            <option value="의뢰비와 함께 지급">의뢰비와 함께 지급</option>
            <option value="해당 없음">해당 없음</option>
        </select>
    </div>
);


}

export default ErrandFee;