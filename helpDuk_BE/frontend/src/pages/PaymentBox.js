import React from 'react';
import RequestFee from './RequestFee';
import ErrandFee from './ErrandFee';
import "../styles/Fee.css";

function PaymentBox({ onPaymentChange }) {
  const handleErrandFeeChange = (name, value) => {
    onPaymentChange(name, value); // 값 변경 시 콜백 함수 호출
  };
  const handleRequestFeeChange = (name, value) => {
    onPaymentChange(name, value);
  };

  return (
    <div className='paymentBox'>
      <div className='fee-container'>
        <RequestFee onRequestFeeChange={handleRequestFeeChange} />
      </div>
      <div className='fee-container'>
      <ErrandFee onErrandFeeChange={handleErrandFeeChange} /> {/* props로 콜백 함수 전달 */}
      </div>
    </div>
  );
}

export default PaymentBox;