import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
// import Cookies from 'universal-cookie';

import CreateQuiz from "./pages/CreateQuiz";
import Library from "./pages/Library";
import NavPages from "./pages/NavPages";
import "./App.css";
import DetailQuiz from "./pages/DetailQuiz";
function App() {
  const emptyProfile = {
    username: "",
    firstname: "",
    lastname: "",
    email: "",
    avatar: "",
  };

  const localToken = window.localStorage.getItem("token");
  const sessionToken = window.sessionStorage.getItem("token");
  const remember = window.localStorage.getItem("remember");
  const localProfile =
    remember === "1"
      ? JSON.parse(window.localStorage.getItem("profile"))
      : null;
  const sessionProfile =
    remember !== "1"
      ? JSON.parse(window.sessionStorage.getItem("profile"))
      : null;
  const [token, setToken] = React.useState(
    localToken ? localToken : sessionToken ? sessionToken : ""
  );
  const [profile, setProfile] = React.useState(
    localProfile ? localProfile : sessionProfile ? sessionProfile : emptyProfile
  );

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="*"
          element={
            <NavPages
              token={token}
              setToken={setToken}
              profile={profile}
              setProfile={setProfile}
            />
          }
        />
        <Route
          path="create-quiz"
          element={<CreateQuiz profile={profile} token={token} />}
        />
        <Route path="test_create_quiz" element={<CreateQuiz profile={profile} token={token} />} />
        <Route path="test_get_quiz" element = {<Library profile={profile} token={token} />}/>
        <Route path="test_play_quiz" element={<DetailQuiz  />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
