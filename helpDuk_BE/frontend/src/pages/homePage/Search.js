import "./Search.css";
import searchIcon from "../../../assets/homePage/SearchIcon.png";

const Search = () => {
    return(
        <div className="search">
            <div className="searchIcon">
                <img src={searchIcon} />
                <h4>검색</h4>
            </div>
            <input className="searchbar" placeholder="검색어를 입력하세요" />
        </div>
    );
};

export default Search;