import "../../styles/ProfileEdit.css"
import RequestsPhoto from "../../assets/image/아마스빈.png";

function SupportLists() {

    
    return (
        <div className="supportLists"  style={{padding: "20px"}}>
            <h1 className="editTitle">받은 의뢰 목록</h1>
            <div className="myList">
                <img className="requestsPhoto" alt="requestsPhoto" src={RequestsPhoto} />
                <div className="ListInfo">
                    <h3>아마스빈 사다주실 분</h3>
                    <h4 style={{color: "#757575"}}>10:30까지</h4>
                    <div className="dealStatus">
                        <h4 className="requestsState">거래 완료</h4>
                        <h3>3,000원</h3>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SupportLists;