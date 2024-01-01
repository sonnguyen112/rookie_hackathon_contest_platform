import { FETCH_USER_LOGIN_SUCCESS, USER_LOGOUT_SUCCESS } from "../action/userAction";

const INITIAL_STATE = {
  account: {
    access_token: "",
    refresh_token: "",
    username: "",
    image: "",
    role: "",
    email: "",
  },
  isAuthenticated: false
}

const userReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case FETCH_USER_LOGIN_SUCCESS:
   
      return {
        ...state,
        account: {
          access_token: action?.payload?.token,
          refresh_token: action?.payload?.refresh_token,
          username: action?.payload?.username,
          email: action?.payload?.email,
          firstname: action?.payload?.first_name,
          lastname: action?.payload?.last_name,
          role: action?.payload?.roles
        },
        isAuthenticated: true
      };

    case USER_LOGOUT_SUCCESS:
      return {
        ...state,
        account: {
          access_token: "",
          refresh_token: "",
          username: "",
          first_name: "",
          role: "",
          last_name: "",
        },
        isAuthenticated: false
      };

    default:
      return state;
  }
};

export default userReducer;
