import axios from "../utils/axiosCustomize";

const postCreateNewUser = (email, password, username, fullname, role, image) => {
  const data = {
    email: email,
    password: password,
    username: username,
    fullname: fullname,
    roles: role,
    avatar: image
  };
  return axios.post("api/auth/signup", data);
};

const getAllusers = () => {
  return axios.get("api/auth/user");
};
const getUserWithPaginate = (page, limit) => {
  return axios.get(`api/auth/user?page=${page}&limit=${limit}`);
};
const getQuizByUser = () => {
  return axios.get("api/v1/quiz/list-quiz");
};
const getDataQuiz = (id) => {
  return axios.get(`api/v1/quiz/do-quiz?quizId=${id}`);
};
const getAllQuizForAdmin = () => {
  return axios.get(`api/v1/quiz/list-quiz`);
};
const getAllQuiz = () => {
  return axios.get(`api/v1/quiz/get_all_quiz`);
};
const getOneQuiz = (id) => {
  return axios.get(`api/v1/quiz/get_one_quiz/${id}` );
};

const deleteQuizForAdmin = (data) => {
  return axios.delete(`api/v1/quiz`, { data: data });
};
const putUpdateUser = (id, email, username, fullname, role) => {


  return axios.put(`api/auth/update/${id}`, {
    email: email,
    username: username,
    fullname: fullname,
    roles: role

  });
};
const deleteUser = (userId) => {
  return axios.delete("api/user", { data: { id: userId } });
};
const postLogin = (username, password) => {
  return axios.post("api/auth/signin", { username, password });
};
const postSubmitQuiz = (quizId, data) => {

  return axios.post(`api/v1/quiz/${quizId}`, data);
};
const postRegister = (fullname, username, email, avatar, password, roles) => {
  return axios.post("api/auth/signup", {
    fullname,
    username,
    email,
    avatar,
    password,
    roles,
  });
};
const postCreateOrUpdateQuiz = (methodS,link,quizCreate) => {
  if(methodS == "POST"){
    return axios.post(link,quizCreate);
  }else{
    return axios.put(link,quizCreate);
  }
 
};

const putUpdateQuizForAdmin = (id, namequiz, imagequiz, description) => {

  const data = new FormData();
  data.append("namequiz", namequiz);
  data.append("description", description);
  data.append("imagequiz", imagequiz);

  return axios.put(`quiz/${id}`, data);
};
const putUpdateQuiz = (id, username, email, password, role, image) => {
  const data = new FormData();
  data.append("email", email);
  data.append("password", password);
  data.append("username", username);
  data.append("roleCode", role);
  data.append("image", image);

  return axios.put("api/user", data);
};
const postCreateNewQuestionForQuiz = (quiz_id, text, image) => {


  return axios.post(`api/questions?quiz_id=${quiz_id}`, {
    text, image
  });
};
const postCreateNewAnswerForQuestion = (description, correcct_answer, question_id) => {
  const data = new FormData();
  data.append("answerText", description);
  data.append("correct", correcct_answer);

  return axios.post(`api/answers/${question_id}`, data);
};
const setCorrectAnswer = (question_id, answer_id) => {

  return axios.post(`api/questions/correctAnswer?question_id=${question_id}&answer_id=${answer_id}`);

};
const getQuizWithQA = (quiz_id) => {

  return axios.post(`api/quiz/${quiz_id}`,);
};

const postUpdateQA = (data) => {

  return axios.post(`api/questions/upsert`, data);
};
const logout = (email, refresh_token) => {

  return axios.post(`logout`);
};

const getOverview = () => {

  return axios.post(`api/v1/overview`);
};
export {
  postCreateNewUser,
  getAllusers,
  putUpdateUser,
  deleteUser,
  getUserWithPaginate,
  postLogin,
  postRegister,
  getQuizByUser,
  getDataQuiz,
  postSubmitQuiz,
  postCreateOrUpdateQuiz,
  putUpdateQuiz,
  getAllQuizForAdmin,
  getAllQuiz,
  putUpdateQuizForAdmin,
  deleteQuizForAdmin,
  getOneQuiz,
  postCreateNewAnswerForQuestion,
  postCreateNewQuestionForQuiz,
  setCorrectAnswer,
  getQuizWithQA,
  postUpdateQA,
  logout,
  getOverview,
};