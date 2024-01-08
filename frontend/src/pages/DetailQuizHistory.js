import { useEffect, useRef, useState } from "react";
import { NavLink, useLocation, useNavigate, useParams } from "react-router-dom";
import '../style/DetailQuiz.scss'
import _ from 'lodash'
import { getQuestionQuizHistory, getDataQuizHistory } from "../services/apiService";
import ModalResult from "./ModalResult";
import QuestionHistory from "./QuestionHistory";
import RightCotent from "./RighContent";
import RightCotentHistory from "./RighContentHistory";


const DetailQuizHistory = () => {
  const params = useParams();
  const location = useLocation();
  const quiz = location.state?.quiz;
  // console.log("Quizzzz:",quiz.duration);
  const quizId = params.id;;
  const [dataQuiz, setDataQuiz] = useState([]);
  const [take, setTake] = useState({});
  const [index, setIndex] = useState(0)
  const [isShowModalResult, setIsShowModalResult] = useState(true)
  const [isShowAnswer, setIsShowAnswer] = useState(true)
  const [dataModalResult, setDataModalResult] = useState({});

  let navigate = useNavigate();
  const [showModelConfirm, setShowModelConfirm] = useState(false);
 

  useEffect(() => {
    fetchQuestions();
    // setShowModelConfirm(true);
    fetchTake();

  }, [quizId]);

  const fetchQuestions = async () => {
    let res = await getDataQuizHistory(quizId);
    let resTake = await getQuestionQuizHistory(+quizId);

    let data = _.chain(res)
      .groupBy("id")
      .map((value, key) => {

        
        let answers = [];
        let questionDescription, image = null;
        value.forEach((item, index) => {

          if (index === 0) {
            questionDescription = item.text;
            image = item.image
          }

          answers = item.answers.map(answer => {
            let select = false;
            let incorr = false;
            resTake.listAnswerTake.forEach(item=>{
              if(item.answers.id == answer.id){
                select = true;
              }
              if(item.answers.correct){
                incorr = true
              }
            })
            return ({ ...answer, isSelected: select, isCorrect: incorr })
          });

          answers = _.orderBy(answers, ['id'], ['asc'])
        })
        return { questionId: key, answers, questionDescription, image }
      })
      .value()

    setDataQuiz(data)

  };


  const fetchTake = async () => {
      let res = await getQuestionQuizHistory(+quizId);
      let data = _.chain(res)
      .value()

      setTake(data);
    }
  
  const handlePrev = () => {
    if (index - 1 < 0) return;
    setIndex(index - 1)
  }
  const handleNext = () => {
    if (dataQuiz.length > index + 1)
      setIndex(index + 1)

      console.log(take)
  }

  const handleBack = () => {
    navigate("/history")
    };

  return (
    <>
      <div className="detail-quiz-container">
        <div className="left-content">
          <div className="title">
            Quiz {quizId} : {quiz.title}
          </div>
          <hr />
          <div className="q-body">
            <img />
          </div>
          <div className="q-content">
            <QuestionHistory
              index={index}
              isShowAnswer={isShowAnswer}
              // handleFinishQuiz={handleFinishQuiz}
              listAnswerTake={take.listAnswerTake}
              data={
                dataQuiz && dataQuiz.length > 0 ? dataQuiz[index] : []
              }
            />


          </div>
          <div className="footer">
            <button className="btn btn-secondary" onClick={() => handlePrev()}>Prev</button>
            <button className="btn btn-primary" onClick={() => handleNext()}>Next</button>
            <button className="btn btn-warning"
              // disabled={isSubtmitQuiz}
              onClick={() => handleBack()}>Back</button>
          </div>
        </div>
        <div className="right-content">
          <RightCotentHistory
            dataQuiz={dataQuiz}
            dataTake={take}
            setIndex={setIndex}
          />
        </div>

       
      </div>

      
    </>
  )};
        

export default DetailQuizHistory;
