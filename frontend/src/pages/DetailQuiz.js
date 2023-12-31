import { useEffect, useRef, useState } from "react";
import { NavLink, useLocation, useNavigate, useParams } from "react-router-dom";
import "../style/DetailQuiz.scss";
import _ from "lodash";

import {
  checkCheating,
  getDataQuiz,
  postSubmitQuiz,
} from "../services/apiService";
import ModalResult from "./ModalResult";
import Question from "./Question";
import RightCotent from "./RighContent";
import ModelConfirmScreen from "./ModelConfirmScreen";
import { toast } from "react-toastify";
import { Backdrop, CircularProgress } from "@mui/material";

const DetailQuiz = () => {
  const params = useParams();
  const location = useLocation();
  const quiz = location.state?.quiz;
  const quizId = params.id;
  const [dataQuiz, setDataQuiz] = useState([]);
  const [imageCheated, setImageCheated] = useState([]);
  const [index, setIndex] = useState(0);
  const [isShowModalResult, setIsShowModalResult] = useState(false);
  const [isShowAnswer, setIsShowAnswer] = useState(false);
  const [isSubtmitQuiz, setIsSubtmitQuiz] = useState(false);
  const [dataModalResult, setDataModalResult] = useState({});
  let navigate = useNavigate();
  const [showModelConfirm, setShowModelConfirm] = useState(false);
  const [loading, setLoading] = useState(false);
  
  

  const videoRef = useRef(null); // create a reference to the video element
  const [stream, setStream] = useState(null); // create a state to store the stream
  const [text, setText] = useState("No");
  const [isVideoVisible, setIsVideoVisible] = useState(false);
  const videoStyle = {
    display: isVideoVisible ? "block" : "none",
  };



  useEffect(() => {
    const getImage = setInterval(async () => {
      //Get base64 of image
      if (stream) {
        const canvas = document.createElement("canvas");
        canvas.width = videoRef.current.videoWidth;
        canvas.height = videoRef.current.videoHeight;
        const ctx = canvas.getContext("2d");
        ctx.drawImage(videoRef.current, 0, 0);
        const dataURL = canvas.toDataURL("image/png");
      
        let res = await checkCheating(dataURL);
        if (res.is_cheat) {
          toast.warning("You are cheating! Please stop cheating!");
          if(imageCheated.length <= 3){
          
            setImageCheated(prevImageCheated => {
              if (prevImageCheated.length < 3) {
                return [...prevImageCheated, dataURL];
              } else {
                return prevImageCheated;
              }
            });

          }
         
        }
      }
    }, 5000);

    return () => clearInterval(getImage);
  }, [stream]);

  const startCapture = async () => {
    try {
      const displayStream = await navigator.mediaDevices.getDisplayMedia({
        video: { displaySurface: "monitor" },
      });
      const displaySurface = displayStream
        .getVideoTracks()[0]
        .getSettings().displaySurface;
      displayStream.getVideoTracks()[0].onended = () => {
        console.log("Stream ended");
         stopCapture();
      };

      if (displaySurface !== "monitor") {
        // Ném ra lỗi để ngăn chặn chia sẻ màn hình
        toast.warning("Selection of entire screen mandatory!");
        displayStream.getTracks().forEach((track) => track.stop());
        return;
      }

      setStream(displayStream);

      videoRef.current.srcObject = displayStream;

      setShowModelConfirm(false);

      setText("Yes");
    } catch (err) {}
  };

 

  useEffect(() => {
    fetchQuestions();
    setShowModelConfirm(true);

    window.addEventListener("beforeunload", handleBeforeUnload);
    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, [quizId]);
  const handleBeforeUnload = (event) => {
    const message =
      "Are you sure you want to stop capture? End Submit Quiz Contest";
    event.returnValue = message;
    return message;
  };

  const fetchQuestions = async () => {
    let res = await getDataQuiz(quizId);
    console.log("data", res);
    if (res.errCode === 14 || res.errCode === 15) {
      toast.warning(res.message);
      navigate("/library");
      return
    }
    let raw = res;

    let data = _.chain(raw)
      .groupBy("id")
      .map((value, key) => {
        let answers = [];
        let questionDescription,
          image = null;
        value.forEach((item, index) => {
          if (index === 0) {
            questionDescription = item.text;
            image = item.image;
          }
          answers = item.answers.map((answer) => ({
            ...answer,
            isSelected: false,
            isCorrect: false,
          }));

          answers = _.orderBy(answers, ["id"], ["asc"]);
        });
        return { questionId: key, answers, questionDescription, image };
      })
      .value();

    setDataQuiz(data);
  };

  const handleCheckbox = (answerId, questionId) => {
    let dataQuizClone = _.cloneDeep(dataQuiz);
    let question = dataQuizClone.find(
      (item) => +item.questionId === +questionId
    );

    if (question && question.answers) {
      let b = question.answers.map((item) => {
        if (+item.id === +answerId) {
          item.isSelected = !item.isSelected;
        } else {
          item.isSelected = false; // Đặt các checkbox khác về false
        }

        return item;
      });

      question.answers = b;
    }

    let index = dataQuizClone.findIndex(
      (item) => +item.questionId === +questionId
    );

    if (index > -1) {
      dataQuizClone[index] = question;
      setDataQuiz(dataQuizClone);
    }
  };


  const stopCapture = async () => {
  

    setShowModelConfirm(true);
    setText("No");

    if (videoRef.current && videoRef.current.srcObject) {
      // Kiểm tra nếu videoRef.current không phải là null hoặc undefined và có srcObject
      videoRef.current.srcObject.getTracks().forEach((track) => track.stop());
      videoRef.current.srcObject = null;
    }

    setStream(null);
  };

  const handleFinishQuiz = async () => {
    setLoading(true);
    let payload = {};
    var answers = [];
    if (dataQuiz && dataQuiz.length > 0) {
      dataQuiz.forEach((question) => {
        let questionId = question.questionId;
        let selectedAnswer = null;

        question.answers.forEach((a) => {
          if (a.isSelected === true) {
            selectedAnswer = a.id;
          }
        });
        answers.push({
          quizQuestion: +questionId,
          selectedAnswer: selectedAnswer,
        });
      });
      payload = answers;
    
      let data = {
        imageCheated: imageCheated,
        answers: answers,
      };
      console.log(data);
      let res = await postSubmitQuiz(+quizId, data);
      setLoading(false);
   
      if (res) {
       
        setShowModelConfirm(false);
   
    
        if (videoRef.current && videoRef.current.srcObject) {
          // Kiểm tra nếu videoRef.current không phải là null hoặc undefined và có srcObject
          videoRef.current.srcObject.getTracks().forEach((track) => track.stop());
          videoRef.current.srcObject = null;
        }
    
        setStream(null);

        setIsSubtmitQuiz(true);
        setDataModalResult({
          correctQuestions: res?.correctQuestions,
          totalQuestions: res?.totalQuestions,
        });
        setIsShowModalResult(true);

        if (res && res.listResult) {
          let dataQuizClone = _.cloneDeep(dataQuiz);

          let a = res.listResult;

          for (let q of a) {
            for (let i = 0; i < dataQuizClone.length; i++) {
              if (+q.id === +dataQuizClone[i].questionId) {
                // UpdateAnswer
                let newAnswers = [];
                for (let j = 0; j < dataQuizClone[i].answers.length; j++) {
                  let s = q.answers.find(
                    (item) => +item.id === +dataQuizClone[i].answers[j].id
                  );

                  if (s) {
                    dataQuizClone[i].answers[j].isCorrect = true;
                  }

                  newAnswers.push(dataQuizClone[i].answers[j]);
                }
                dataQuizClone[i].answers = newAnswers;
              }
            }
          }
          setDataQuiz(dataQuizClone);
        }
      } else {
        alert("Wrong");
      }
    }
  };
  const handlePrev = () => {
    if (index - 1 < 0) return;
    setIndex(index - 1);
  };
  const handleNext = () => {
    if (dataQuiz.length > index + 1) setIndex(index + 1);
  };

  const handleBack = () => {
  navigate("/")
  };

  return (
    <>
    <Backdrop
  sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
  open={loading}

>
  <CircularProgress color="inherit" />
</Backdrop>
      <video ref={videoRef} autoPlay style={videoStyle} />
      <div className="detail-quiz-container">
        <div className="left-content">
          <div className="title">
            Quiz {quizId} : {location?.state?.quizTitle}
          </div>
          <hr />
          {/* <div className="q-body">
            <img />
          </div> */}
          <div className="q-content">
            <Question
              index={index}
              handleCheckbox={handleCheckbox}
              isShowAnswer={isShowAnswer}
              isSubtmitQuiz={isSubtmitQuiz}
              data={dataQuiz && dataQuiz.length > 0 ? dataQuiz[index] : []}
            />
          </div>
          <div className="footer">
            <button className="btn btn-secondary" onClick={() => handlePrev()}>
              Prev
            </button>
            <button className="btn btn-primary" onClick={() => handleNext()}>
              Next
            </button>
            <button
            disabled={isSubtmitQuiz}
            className="btn btn-success" onClick={handleFinishQuiz}>Submit</button>

            <button
              className="btn btn-warning"
              hidden={!isSubtmitQuiz}
              onClick={() => handleBack()}
            >
              Back 
            </button>
          </div>
        </div>
        <div className="right-content">
          <RightCotent
            dataQuiz={dataQuiz}
            duration={quiz.duration}
            handleFinishQuiz={handleFinishQuiz}
            setIndex={setIndex}
          />
        </div>
        <ModalResult
          show={isShowModalResult}
          setShow={setIsShowModalResult}
          setIsShowAnswer={setIsShowAnswer}
          dataModalResult={dataModalResult}
        />
      </div>

      <ModelConfirmScreen
        show={showModelConfirm}
        setShow={setShowModelConfirm}
        startCapture={startCapture}
        stopCapture={stopCapture}
        text={text}
        videoRef={videoRef}
      />
    </>
  );
};

export default DetailQuiz;
