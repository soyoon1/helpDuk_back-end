
import { useState } from "react";
import "./HomePage.css";
import HomepageHeader from "./HomepageHeader"
import RequestList from "./RequestList";

function HomePage() {

    return(
        <div className="homepage">
            <HomepageHeader />
            <RequestList /> 
        </div>
    );
}

export default HomePage;