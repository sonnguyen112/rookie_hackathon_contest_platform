import BLASKItem from "../components/BlaskItem";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "../App.css";
import { Backdrop, CircularProgress } from "@mui/material";
import { getAllQuiz, getOneQuiz, deleteOneQuiz } from "../services/apiService";

const Library = (props) => {
  let navigate = useNavigate();
  const [quizs, setQuizs] = useState(Array(0).fill(null));
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    async function GetData() {
      let data = await getAllQuiz();
   

      // let data = await response.json(); // parses JSON response into native JavaScript objects
      console.log(data);
      setQuizs(data);
      setLoading(false);
    }
    GetData();
  }, [loading]);

  const handleEditQuiz = (index) => {
    setLoading(true);
    async function fetchQuiz() {
      // const response = await fetch(
      //   "http://localhost:8080/api/v1/quiz/get_one_quiz/" + quizs[index]["slug"],
      //   {
      //     mode: "cors",
      //     method: "GET",
      //     headers: [
      //       ["Content-Type", "application/json"],
      //       ["Authorization", "token " + props.token],
      //     ],
      //   }
      // );
      let editQuiz = await getOneQuiz(quizs[index]["slug"]);
      
      console.log("before edit", editQuiz);
      setLoading(false);
      navigate("/create-quiz", {
        state: { edit: 1, quiz: editQuiz, slug: quizs[index]["slug"] },
      });
    }
    console.log("edit", index);
    fetchQuiz();
  };
  const handleDeleteQuiz = (index) => {
    console.log("delete", index);
    var copyQuizs = [...quizs];
    copyQuizs.splice(index, 1);
    async function fetchDeleteQuiz() {
      // const response = await fetch(
      //   `http://localhost:8080/api/v1/quiz/delete_one_quiz/${quizs[index]["slug"]}`,
      //   {
      //     mode: "cors",
      //     method: "DELETE",
      //     headers: [
      //       ["Content-Type", "application/json"],
      //       ["Authorization", "token " + props.token],
      //     ],
      //   }
      // );
      await deleteOneQuiz(quizs[index]["slug"]);
      setQuizs(copyQuizs);
    }
    fetchDeleteQuiz();
  };

  // async function handlePlayQuiz(index) {

    // const response = await fetch('http://localhost:8000/room/api/get_quiz/' , {
    //   method: 'GET', // *GET, POST, PUT, DELETE, etc.
    //   mode: 'cors', // no-cors, *cors, same-origin
    //   headers: {
    //     'Content-Type': 'application/json',
    //     'Authorization': 'token ' + props.token
    //   },
    // });
    // const response2 = await fetch(
    //   "http://localhost:8000/room/api/create_room/" + quizs[index]["slug"],
    //   {
    //     method: "GET", // *GET, POST, PUT, DELETE, etc.
    //     mode: "cors", // no-cors, *cors, same-origin
    //     headers: {
    //       "Content-Type": "application/json",
    //       Authorization: "token " + props.token,
    //     },
    //   }
    // );
    // // let data = await response.json()
    // let data2 = await response2.json();
    // navigate("/room", {
    //   state: {
    //     // question_info: data,
    //     quiz_info: data2,
    //     /*
    //     pin:
    //     token_host:
    //     title:
    //     description:
    //     list_question:
    //     list_option:
    //   */
    //     my_token: props.token,
    //   },
    // });
  // }
  return (
    <div className="blask-list">
      <Backdrop open={loading} sx={{ zIndex: 100 }}>
        <CircularProgress color="primary" />
      </Backdrop>
      {quizs.map((item, index) => (
        <BLASKItem
       
          // avatar={props?.profile.avatar}
          value={item}
          deleteQuiz={() => handleDeleteQuiz(index)}
          editQuiz={() => handleEditQuiz(index)}
          onClick={() =>
            navigate(`/play_quiz/${item.id}`, {
              state: { quiz: item },
            })
          }
        ></BLASKItem>
      ))}
    </div>
  );
};
export default Library;
