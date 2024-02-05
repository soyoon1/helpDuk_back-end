import "./HomepageHeader.css";
import {Link} from "react-router-dom";

const HomepageHeader = () => {
    return (
        <div className="homepageHeader">
            <h1>의뢰 목록</h1>
            <div className="writeButton">
                <Link to={"/requestForm"}>
                    <h4>의뢰하기</h4>
                </Link>
            </div>
        </div>
    );
};

export default HomepageHeader;