import "../styles/Footer.css";
import logImage from '../assets/image/푸터_부름부릉.png';

function Footer() {
    return (
        <div className="footer">
            <img className="log" alt="buleumbuleung" src={logImage} />
            <div className="footerText">
                <h5>덕성여자대학교 컴퓨터공학전공 소모임 코너 코너톤 4팀 헬프덕</h5>
                <h5>개발자 : 서지혜, 심수빈, 유채민, 전효민, 정민주, 조소윤</h5>
            </div>
            <div className="copyright">
                <h6>Copyright © helpDuk.  All right reserved.</h6>
            </div>
        </div>
    )
}

export default Footer;
