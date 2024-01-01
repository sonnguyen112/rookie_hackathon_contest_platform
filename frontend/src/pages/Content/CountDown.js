import { useEffect } from "react";
import { useState } from "react";

const CountDown = (props) => {
    const [count, setCount] = useState(300)
    useEffect(() => {
        if (count === 0) {
            props.onTimeUp();
            return
        }
        const timer = setInterval(() => {
            setCount(count - 1)
        }, 1000)
        return () => {
            clearInterval(timer)
        }
    }, [count])
    const toHHMMSS = (secs) => {
        var sec_num = parseInt(secs, 10); // don't forget the second param
        var hours = Math.floor(sec_num / 3600);
        var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
        var seconds = sec_num % 60;


        return [hours, minutes, seconds]
            .map(v => v < 10 ? "0" + v : v)
            .filter((v, i) => v !== "00" || i > 0)
            .join(":")

    }

    return (<div className="coutdown-container">
        {toHHMMSS(count)}
    </div>);
}

export default CountDown;