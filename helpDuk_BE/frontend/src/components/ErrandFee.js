import React, { useState } from 'react';
import "../styles/Fee.css";

function ErrandFee() {
  const [errandCost, setErrandCost] = useState('');
  const [errandPaymentMethod, setErrandPaymentMethod] = useState('');

  const handleErrandCostChange = (e) => {
    setErrandCost(e.target.value);
  };

  const handleErrandPaymentMethodChange = (e) => {
    setErrandPaymentMethod(e.target.value);
  };

  return (
    <div className='fee'>
        <h3>심부름 비용</h3>
        <p>심부름 비용은 선불입니다.</p>
        <input type="text" value={errandCost} onChange={handleErrandCostChange} placeholder="0원" className='custom-money' />
        <select value={errandPaymentMethod} onChange={handleErrandPaymentMethodChange} className="custom-select">
            <option value="">지불방법</option>
            <option value="계좌 이체">계좌 이체</option>
            <option value="해당 없음">해당 없음</option>
        </select>
    </div>
);

    
}

export default ErrandFee;