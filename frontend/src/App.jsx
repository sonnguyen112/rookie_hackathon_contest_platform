import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
// import Cookies from 'universal-cookie';

import CreateQuiz from "./pages/CreateQuiz";
import Library from "./pages/Library";
import NavPages from "./pages/NavPages";
import "./App.css";
import DetailQuiz from "./pages/DetailQuiz";
import DetailQuizHistory from "./pages/DetailQuizHistory";
import { Provider, useSelector } from "react-redux";
import { persistor, store } from "./redux/store";
import { PersistGate } from "redux-persist/integration/react";
import { ToastContainer } from "react-toastify";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import "bootstrap/dist/css/bootstrap.min.css";
function App() {
  const emptyProfile = {
    username: "",
    firstname: "",
    lastname: "",
    email: "",
    avatar: "",
  };


  

  return (
    <Provider store={store}>
       <PersistGate loading={null} persistor={persistor}>
    <BrowserRouter>
      <Routes>
        <Route
          path="*"
          element={
            <NavPages
            />
          }
        />
        <Route
          path="create-quiz"
          element={<CreateQuiz />}
        />
        <Route path="test_create_quiz" element={<CreateQuiz  />} />
        <Route path="test_get_quiz" element = {<Library  />}/>
        <Route path="play_quiz/:id" element={<DetailQuiz  />} />
        <Route path="history_quiz/:id" element={<DetailQuizHistory  />} />
        
      </Routes>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
      <ToastContainer />
      </BrowserRouter>
    </PersistGate>
    </Provider>
  );
}

export default App;
