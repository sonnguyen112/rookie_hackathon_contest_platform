import { deleteQuizForAdmin, getAllQuizForAdmin, postCreateNewQuiz } from "../../services/apiService";
import { CREATE_QUIZ_ERROR, CREATE_QUIZ_REQUEST, CREATE_QUIZ_SUCCESS, DELETE_QUIZ_SUCCESS, FETCH_QUIZ_ERROR, FETCH_QUIZ_REQUEST, FETCH_QUIZ_SUCCESS } from "./types";

export const fetchAllQuiz = () => {
    return async (dispath, getState) => {
        dispath(fetchUserRequest());

        try {
            const res = await getAllQuizForAdmin();
            const data = res ? res : [];
            dispath(fetchQuizSuccess(data))
        } catch (error) {
            dispath(fetchQuizError)
        }


    };
};


export const fetchUserRequest = () => {
    return {
        type: FETCH_QUIZ_REQUEST

    };
};

export const fetchQuizSuccess = (data) => {
    return {
        type: FETCH_QUIZ_SUCCESS,
        dataQuiz: data
    };
};


export const fetchQuizError = () => {
    return {
        type: FETCH_QUIZ_ERROR

    };
};

export const createUserRequest = () => {
    return {
        type: CREATE_QUIZ_REQUEST

    };
};


export const createUserSuccess = () => {
    return {
        type: CREATE_QUIZ_SUCCESS

    };
};


export const createUserError = () => {
    return {
        type: CREATE_QUIZ_ERROR

    };
};

export const createNewUserRedux = (name, image, description) => {
    return async (dispath, getState) => {
        dispath(createUserRequest());

        try {
            const res = await postCreateNewQuiz(name, image, description);
            if (res) {
                dispath(createUserSuccess())
                dispath(fetchAllQuiz())
            }

        } catch (error) {
            dispath(createUserError)
        }


    };
};
export const deleteUserSuccess = () => {
    return {
        type: DELETE_QUIZ_SUCCESS

    };
};

export const deleteUserRedux = (id) => {
    return async (dispath, getState) => {
        dispath(createUserRequest());

        try {
            const res = await await deleteQuizForAdmin(id)
            if (res) {
                dispath(deleteUserSuccess())
                dispath(fetchAllQuiz())
            }

        } catch (error) {
            dispath(createUserError)
        }


    };
};

