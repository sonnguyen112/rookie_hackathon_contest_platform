import { useRef } from "react";
import CountDown from "./Content/CountDown";


const RightCotentHistory = (props) => {
    const { dataQuiz, dataTake } = props;
    // console.log("Duration", duration)

    const refDiv = useRef([])
    const onTimeUp = () => {
        props.handleFinishQuiz()
    }

    const getClassQuestion = (index, question) => {
        if (question && question.answers.length > 0) {
            let isAnswered = question.answers.find(a => a.isSelected === true)
            if (isAnswered) {
                return "question selected "
            }
        }
        return "question right-correct";
    }

    const handleClickQuestion = (index, question) => {
        props.setIndex(index)
        if (refDiv.current) {
            refDiv.current.forEach(item => {
                if (item && item.className === "question clicked") {
                    item.className = "question"
                }
            })
        }

        if (question && question.answers.length > 0) {
            let isAnswered = question.answers.find(a => a.isSelected === true)
            if (isAnswered) {
                return;
            }
        }
        refDiv.current[index].className = " question clicked";

    }

    return (<>
        <div className="main-timer">
            <h2>Lịch sử làm bài</h2>
            <p>Tổng số câu: {dataTake.totalQuestions}</p>
            <p>Số câu đúng: {dataTake.correctQuestions}</p>
            <p>Điểm: {dataTake.score}</p>
        </div>

        <div className="main-question">
            {dataQuiz && dataQuiz.length > 0 && dataQuiz.map((item, index) => {
                return (
                    <div
                        key={`question-${index}`}
                        className={getClassQuestion(index, item)}
                        onClick={() => handleClickQuestion(index, item)}
                        ref={element => refDiv.current[index] = element}
                    >
                        {index + 1}
                    </div>
                )
            })}
        </div>
    </>);
}

export default RightCotentHistory;