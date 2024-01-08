import axios from "axios";
import { store } from "../redux/store";

const instance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
});

instance.interceptors.request.use(
  function (config) {
    // Do something before request is sent

    const token = store?.getState()?.user?.account?.access_token;

    config.headers = {
      "Authorization": `Bearer ${token}`
    };
    return config;
  },
  function (error) {

    return Promise.reject(error);
  }
);

// Add a response interceptor
instance.interceptors.response.use(
  function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data

    return response && response.data ? response.data : response;
  },
  function (error) {
    // console.log("error.response.data", error.response.data)
    // axios retry refreshtoken
    // if (error.response.data) {
    //   window.location.href = '/login'
    // }

    return error && error.response && error.response.data
      ? error.response.data
      : Promise.reject(error);
  }
);

export default instance;
