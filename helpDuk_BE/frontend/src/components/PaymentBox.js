import React from 'react';
import RequestFee from './RequestFee';
import ErrandFee from './ErrandFee';
import "../styles/Fee.css";

function PaymentBox() {
  return (
    <div className='paymentBox'>
      <div className='fee-container'>
        <RequestFee />
      </div>
      <div className='fee-container'>
        <ErrandFee />
      </div>
    </div>
  );
}

export default PaymentBox;