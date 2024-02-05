import "./DetailPage.css";
import SampleImage from '../../assets/detailPage/printer.png';
import Profile from "./Profile";
import DetailContents from "./DetailContents";

function DetailPage({mockRequest}) { 
    return(
        <div className="detailpage">
            <div className="leftSide">

                <Profile />
            </div>
            <div className="rightSide">
                <DetailContents />
            </div>
        </div>
    );
}

export default DetailPage;