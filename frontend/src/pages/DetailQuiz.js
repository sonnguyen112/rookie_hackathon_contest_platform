import { useEffect, useState } from "react";
import { NavLink, useLocation, useParams } from "react-router-dom";
// import { getDataQuiz, postSubmitQuiz } from "../../services/apiService";

import '../style/DetailQuiz.scss'
import _ from 'lodash'


import { getDataQuiz, postSubmitQuiz } from "../services/apiService";
import ModalResult from "./ModalResult";
import Question from "./Question";
import RightCotent from "./RighContent";
const DetailQuiz = () => {
  const params = useParams();
  const location = useLocation();
  const quizId = params.id;;
  const [dataQuiz, setDataQuiz] = useState([]);
  const [index, setIndex] = useState(0)
  const [isShowModalResult, setIsShowModalResult] = useState(false)
  const [isShowAnswer, setIsShowAnswer] = useState(false)
  const [isSubtmitQuiz, setIsSubtmitQuiz] = useState(false)
  const [dataModalResult, setDataModalResult] = useState({});

  useEffect(() => {
    fetchQuestions();
  }, [quizId]);

  const fetchQuestions = async () => {

    let res = await getDataQuiz(quizId);
    let raw = res

    let data = _.chain(raw)
      .groupBy("id")
      .map((value, key) => {

        
        let answers = [];
        let questionDescription, image = null;
        value.forEach((item, index) => {

          if (index === 0) {
            questionDescription = item.text;
            image = item.image
          }
          answers = item.answers.map(answer => ({ ...answer, isSelected: false, isCorrect: false }));

          answers = _.orderBy(answers, ['id'], ['asc'])
        })
        return { questionId: key, answers, questionDescription, image }
      })
      .value()

    setDataQuiz(data)

  };

  const handleCheckbox = (answerId, questionId) => {

    let dataQuizClone = _.cloneDeep(dataQuiz);
    let question = dataQuizClone.find(item => +item.questionId === +questionId)

    if (question && question.answers) {

      let b = question.answers.map(item => {


        if (+item.id === +answerId) {
          item.isSelected = !item.isSelected
        } else {
          item.isSelected = false; // Đặt các checkbox khác về false
        }

        return item

      })

      question.answers = b;
    }

    let index = dataQuizClone.findIndex(item => +item.questionId === +questionId)

    if (index > -1) {

      dataQuizClone[index] = question;
      setDataQuiz(dataQuizClone)
    }
  }



  const handleFinishQuiz = async () => {
    let payload = {};
    var answers = [];
    if (dataQuiz && dataQuiz.length > 0) {

      dataQuiz.forEach(question => {
        let questionId = question.questionId;
        let selectedAnswer = null;

        question.answers.forEach(a => {
          if (a.isSelected === true) {

            selectedAnswer = a.id
          }
        })
        answers.push({
          quizQuestion: +questionId,
          selectedAnswer: selectedAnswer
        })

      })
      payload = answers;
      
      let res = await postSubmitQuiz(+quizId, answers);

      if (res) {
       
        setIsSubtmitQuiz(true)
        setDataModalResult({
          correctQuestions: res?.correctQuestions,
          totalQuestions: res?.totalQuestions,
     
        })
        setIsShowModalResult(true)

        if (res && res.listResult) {

          let dataQuizClone = _.cloneDeep(dataQuiz);

          let a = res.listResult;

          for (let q of a) {

            for (let i = 0; i < dataQuizClone.length; i++) {

              if (+q.id === +dataQuizClone[i].questionId) {
                // UpdateAnswer
                let newAnswers = [];
                for (let j = 0; j < dataQuizClone[i].answers.length; j++) {

                  let s = q.answers.find(item => +item.id === +dataQuizClone[i].answers[j].id)


                  if (s) {

                    dataQuizClone[i].answers[j].isCorrect = true;

                  }

                  newAnswers.push(dataQuizClone[i].answers[j])
                }
                dataQuizClone[i].answers = newAnswers;
              }

            }
          }
          setDataQuiz(dataQuizClone)
        }
      } else {

        alert("Wrong")
      }
    }
  }
  const handlePrev = () => {
    if (index - 1 < 0) return;
    setIndex(index - 1)
  }
  const handleNext = () => {
    if (dataQuiz.length > index + 1)
      setIndex(index + 1)
  }

  return (
    <>
   
      <div className="detail-quiz-container">
        <div className="left-content">
          <div className="title">
            Quiz {quizId} : {location?.state?.quizTitle}
          </div>
          <hr />
          <div className="q-body">
            <img />
          </div>
          <div className="q-content">
            <Question
              index={index}
              handleCheckbox={handleCheckbox}
              isShowAnswer={isShowAnswer}
              isSubtmitQuiz={isSubtmitQuiz}
              data={
                dataQuiz && dataQuiz.length > 0 ? dataQuiz[index] : []
              }
            />


          </div>
          <div className="footer">
            <button className="btn btn-secondary" onClick={() => handlePrev()}>Prev</button>
            <button className="btn btn-primary" onClick={() => handleNext()}>Next</button>
            <button className="btn btn-warning"
              disabled={isSubtmitQuiz}
              onClick={() => handleFinishQuiz()}>Finish</button>
          </div>
        </div>
        <div className="right-content">
          <RightCotent
            dataQuiz={dataQuiz}
            handleFinishQuiz={handleFinishQuiz}
            setIndex={setIndex}
          />
        </div>
        <ModalResult show={isShowModalResult}
          setShow={setIsShowModalResult}
          setIsShowAnswer={setIsShowAnswer}
          dataModalResult={dataModalResult}
        />
      </div>
    </>
  );
};

export default DetailQuiz;
