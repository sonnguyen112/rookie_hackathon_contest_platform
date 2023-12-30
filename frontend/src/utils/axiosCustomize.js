import axios from "axios";


const instance = axios.create({
  baseURL: "http://localhost:8081/",
});

instance.interceptors.request.use(
  function (config) {
    // Do something before request is sent

    

    config.headers = {
      "Authorization": `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTcwMzk0NDMyOCwiZXhwIjoxNzA0MDMwNzI4fQ.Q4UGFMzsD9hR-553zP7_uShDMDMVPetJndHt1Prbn3HU-KbpCQr8mvtGX2o3MWF7hvpszZpgCJbMRtMHHszYYA`
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
