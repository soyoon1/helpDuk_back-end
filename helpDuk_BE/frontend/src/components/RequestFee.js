import React, { useState } from 'react';
import "../styles/Fee.css";

function RequestFee() {
  const [cost, setCost] = useState('');
  const [paymentMethod, setPaymentMethod] = useState('');

  const handleCostChange = (e) => {
    setCost(e.target.value);
  };

  const handlePaymentMethodChange = (e) => {
    setPaymentMethod(e.target.value);
  };

  return (
    <div className='fee'>
        <h3>의뢰 비용</h3>
        <p>0원 심부름은 해당없음을 눌러주세요.</p>
        <input type="text" value={cost} onChange={handleCostChange} placeholder="0원" className='custom-money' />
        <select value={paymentMethod} onChange={handlePaymentMethodChange} className="custom-select">
            <option value="">지불방법</option>
            <option value="계좌 이체">계좌 이체</option>
            <option value="현금">현금</option>
            <option value="심부름 비용과 함께 지급">심부름 비용과 함께 지급</option>
            <option value="해당 없음">해당 없음</option>
        </select>
    </div>
  );
}

export default RequestFee;