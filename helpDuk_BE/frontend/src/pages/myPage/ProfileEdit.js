import { useNavigate  } from "react-router-dom";
import profileImage from '../../assets/image/user.png';
import "../../styles/ProfileEdit.css"
import { useUser } from '../../components/UserContext';
import { useState } from "react";

function ProfileEdit () {
    const navigate = useNavigate();
    const { userImage, setUserImage, nickname, setNickname } = useUser();
    const [isBlank, setIsBlank] = useState(false);

    const [tempImage, setTempImage] = useState(userImage);
    const [tempNickname, setTempNickname] = useState(nickname);
    
    const handleImageChange = (e) => {
        if (e.target.files && e.target.files[0]) {
            setTempImage(URL.createObjectURL(e.target.files[0]));
            e.target.value = ''; // 삭제한 사진 다시 업로드 가능하게 함
        }
    };

    const resetImage = () => {
        setTempImage(profileImage);
    };

    const handleNicknameChange = (e) => {
        setTempNickname(e.target.value);
        setIsBlank(e.target.value.trim() === '');
    };

    const handleSubmit = () => {
        if (!isBlank && tempNickname.trim() !== '') {
            setUserImage(tempImage);
            setNickname(tempNickname);
            navigate('../Mypage');
        }
    };

    return (
        <div className="profileEdit"  style={{padding: "40px"}}>
            <div className="editTitle">
                <h1>프로필 수정</h1>
                <button className="submitButton" onClick={handleSubmit}>
                    <h4>완료</h4>
                </button>
            </div>
            <div className="editInfo">
                <table style={{marginBottom: "40px"}}>
                    <tr>
                        <td style={{fontSize: "1.2em"}}>프로필 사진</td>
                        <td>
                        <div className="editImage">
                            <img className="profile" alt="profile" src={tempImage} />
                            <input type="file" onChange={handleImageChange} style={{ display: 'none' }} id="fileInput" accept="image/png, image/jpeg, image/gif"/>
                            <h4 className="profileButton" onClick={() => document.getElementById('fileInput').click()}>사진변경</h4>
                            <h4 className="profileButton" onClick={resetImage}>사진삭제</h4>
                        </div>
                        </td>
                    </tr>
                    <tr className="nickName">
                        <td style={{fontSize: "1.2em"}}>닉네임</td>
                        <td>
                        <input 
                        placeholder="nickname" 
                        value={tempNickname} 
                        onChange={handleNicknameChange}
                        />
                        {isBlank && ( <p>빈칸은 입력할 수 없습니다.</p>)}
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    )
}

export default ProfileEdit;