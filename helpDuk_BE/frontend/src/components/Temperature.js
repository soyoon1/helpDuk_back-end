import "./Temperature.css"

function Temperature ({userTemperature}) {
    const temperaturePercentage = Math.min(userTemperature, 100);

    return (
        <div className="temperatureInfo">
            <div className="temperatureBarContainer">
                <div className="temperatureBar" style={{width: `${temperaturePercentage}%`}}></div>
            </div>
        </div>
    );
}

export default Temperature;