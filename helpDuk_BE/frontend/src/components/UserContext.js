import React, { useState, createContext, useContext } from 'react';
import profileImage from '../assets/image/user.png';

const UserContext = createContext();

export const useUser = () => useContext(UserContext);

export const UserProvider = ({ children }) => {
    const [userImage, setUserImage] = useState(profileImage);
    const [nickname, setNickname] = useState('User');
    const [userTemperature, setUserTemperature] = useState(90);

    return (
        <UserContext.Provider value={{ userImage, setUserImage, nickname, setNickname, userTemperature, setUserTemperature }}>
            {children}
        </UserContext.Provider>
    );
};