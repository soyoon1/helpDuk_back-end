import React, { useState, useEffect } from 'react';
import AlertBox from './AlertBox'; 
import "../styles/AlertList.css";

const AlertList = ({ alerts }) => {
  // 최대 알림 개수 설정
  const MAX_ALERTS = 5;

  // 알림 목록을 유지하기 위한 배열
  const [alertList, setAlertList] = useState([]);

  // 새로운 알림 추가 시 최대 알림 개수 유지하도록 처리
  useEffect(() => {
    if (alerts.length > 0) {
      const updatedAlerts = [...alerts].slice(0, MAX_ALERTS);
      setAlertList(updatedAlerts);
    }
  }, [alerts]);

  const addNewAlert = (newAlert) => {
    if (alertList.length >= MAX_ALERTS) {
      // 현재 알림 개수가 최대 개수 이상일 때, 가장 오래된 알림을 삭제하고 새로운 알림 추가
      const updatedAlerts = [...alertList.slice(1), newAlert];
      setAlertList(updatedAlerts);
    } else {
      // 최대 개수 미만일 때는 그냥 새로운 알림을 추가
      setAlertList(prevAlerts => [newAlert, ...prevAlerts]);
    }
  };

  return (
    <div className="alert-list">
      {alertList.map((alert, index) => (
        <AlertBox key={index} review={alert.review} timestamp={alert.timestamp} />
      ))}
    </div>
  );
};

export default AlertList;
