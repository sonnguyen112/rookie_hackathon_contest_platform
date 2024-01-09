import {Backdrop, Box, CircularProgress} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import { getAllQuiz, getAllHistoryByUser} from "../services/apiService";
import BLASKItemHistory from "../components/BlaskItemHistory";

const HistoryPage = (props) => {
    let navigate = useNavigate();
  const [quizs, setQuizs] = useState(Array(0).fill(null));
  const [quiz_new, setQuizs_new] = useState(Array(0).fill(null));
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    async function GetData() {
      setLoading(true)
      let data = await getAllQuiz();
      let data_new  = await getAllHistoryByUser();
   
      setQuizs(data);
      setQuizs_new(data_new);
      setLoading(false);
    }
    GetData();
  }, []);

  
  return (
    <div className="blask-list">
      <Backdrop
        sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>

      {quiz_new.map((item, index) => {
        let quiz = quizs.find((i) => i.id == item.quizId)
        return (
          <BLASKItemHistory
         
            // avatar={props?.profile.avatar}
            value={item}
            quiz={quiz}
            onClick={() =>
              navigate(`/history_quiz/${quiz.id}`, {
                state: { quiz: quiz },
              })
            }
          ></BLASKItemHistory>
        )
      })}
    </div>
  );
};

export default HistoryPage;
