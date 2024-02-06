import React, { useState, useEffect } from 'react';
import "./RequestList.css";
import Request from "./Request";
import Category from "./Category";
import searchIcon from "../../assets/homePage/SearchIcon.png";
import dropdownUp from "../../assets/homePage/up.png";
import dropdownDown from "../../assets/homePage/down.png";
import { useMock } from '../../components/MockContext';
import { useParams } from 'react-router-dom';

const RequestList = () => {
    const [dropdownVisibility, setDropdownVisibility] = useState(false);
    const [keyword, setKeyword] = useState('');

    const { mockDate, setMockDate } = useMock();
    const { taskId } = useParams();
    const requestIndex = mockDate.findIndex(request => request.taskId === +taskId);
    const request = mockDate[requestIndex];

    //카테고리 별 선택 여부 저장
    const [selectedCategories, setSelectedCategories] = useState({
        onlyYet: false,
        onlyMine: false,
        school: false,
        dormitory: false,
        etc: false,
        print: false,
        food: false,
        coverFor: false,
        clean: false,
        eventAssistant: false,
        bug: false,
    });
    //console.log("카테고리 별 선택 여부:" ,selectedCategories);
    //선택된 카테고리(값이 true) selectedCategories의 key값만 따로 저장
    const selectedKeys = Object.keys(selectedCategories).filter(key => selectedCategories[key] === true);
    //console.log("선택된 카테고리:", selectedKeys);
    

    const handleCategoryChange = (updatedCategories) => {
        // 업데이트된 카테고리 정보를 상태로 설정
        setSelectedCategories(updatedCategories);
    };


    const filteredRequests = mockDate.filter(request =>
        //검색에 따라 필터링(제목, 내용, 카테고리 검색 가능)
        request.title.toLowerCase().includes(keyword.toLowerCase()) ||
        request.content.toLowerCase().includes(keyword.toLowerCase()) ||
        request.category.toLowerCase().includes(keyword.toLowerCase()) 

        // 카테고리에 따라 필터링 추가
    );
    console.log(filteredRequests);

    const handleSearchSubmit = (e) => {
        e.preventDefault();
    };

    return (
        <div className="requestList">
            <div className="list_wrapper_container">
                <div className="list_wrapper_area"><hr/></div>
                <div className="list_wrapper">
                    {/* Request 컴포넌트에 필터된 내용 props로 전달 */}
                    <Request requests={filteredRequests} />
                </div>
            </div>
    
            <div className="filtering">
                <form onSubmit={handleSearchSubmit}>
                    <div className="search">
                        <div className="searchIcon">
                            <img src={searchIcon} alt="Search Icon" />
                            <h4>검색</h4>
                        </div>
                        <input
                            className="searchbar"
                            placeholder="검색어를 입력하세요"
                            value={keyword}
                            onChange={(e) => setKeyword(e.target.value)}
                        />
                    </div>
                </form>
                <button className="categoryButton" onClick={() => setDropdownVisibility(!dropdownVisibility)}>
                    {dropdownVisibility ? <h4><img src={dropdownDown} alt="Dropdown Down" /> 카테고리</h4>
                        : <h4><img src={dropdownUp} alt="Dropdown Up" /> 카테고리</h4>
                    }
                </button>
                <Category
                visibility={dropdownVisibility}
                onCategoryChange={handleCategoryChange} // 카테고리 변경 시 호출할 콜백 함수 전달
            />
            </div>
        </div>
    );
};

export default RequestList;


// import React from 'react';
// import "./RequestList.css";
// import Request from "./Request";
// import Category from "./Category";
// import searchIcon from "../../assets/homePage/SearchIcon.png";
// import dropdownUp from "../../assets/homePage/up.png";
// import dropdownDown from "../../assets/homePage/down.png";
// import { useState } from "react";
// import { useMock } from '../../components/MockContext';
// import { useParams } from 'react-router-dom';


// const RequestList = () => {
//     const [dropdownVisibility, setDropdownVisibility] = React.useState(false);

//     const [keyword, setKeyword] = useState("");
//     const onChangeKeyword = (e) => {
//         setKeyword(e.target.value);
//     };

//     const { mockDate, setMockDate } = useMock();
//     const { taskId } = useParams();
    
//     const requestIndex = mockDate.findIndex(request => request.taskId === +taskId);
//     const request = mockDate[requestIndex];

//     const getSearchResult = () => {
//     return keyword === "" ? [request] : [request].filter((item) => item.content.includes(keyword));
// };

//     return(
//         <div className="requestList">
//             <div className="list_wrapper">
//                 {getSearchResult().map((item) => (
//                     <Request key={item.taskId} {...item} />
//                 ))}
//             </div>

//             <div className="filtering">
//                 <div className="search">
//                     <div className="searchIcon">
//                         <img src={searchIcon} />
//                         <h4>검색</h4>
//                     </div>
//                     <input className="searchbar" 
//                     value={keyword} onChange={onChangeKeyword}
//                     Chaplaceholder="검색어를 입력하세요" />
//                 </div>
//                 <button className="categoryButton" onClick={e => setDropdownVisibility(!dropdownVisibility)}>
//                         {
//                             dropdownVisibility
//                                 ? <h4><img src={dropdownDown} /> 카테고리</h4>
//                                 : <h4><img src={dropdownUp} /> 카테고리</h4>
//                         }
//                     </button>
//                     <Category visibility={dropdownVisibility} />
//             </div>   
//         </div>
//     );
// };

// export default RequestList;