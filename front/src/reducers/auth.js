import {
    AUTH_FAILED,
    LOG_IN_SUCEED,
    LOG_OUT_SUCCEED,
    UPDATE_CURRENT_USER
} from "../actions/auth"

const defaultState = {
    currentUser: null,
    errorMessage: null
//   currentUser: {
//     id: 'admin',
//     name: '管理者',
//     token: '**********'
//   }
}

export default function auth(state = defaultState, action) {
    switch (action.type) {
        case UPDATE_CURRENT_USER: {
            const currentUser = {
                ...(state.currentUser || {}),
                ...action.payload.data
            }
            return {
                ...state,
                currentUser
            }
        }
        case LOG_IN_SUCEED: {
            const {currentUser} = action.payload
            return {
                ...state,
                currentUser
            }
        }
        case LOG_OUT_SUCCEED: {
            return {
                ...state,
                currentUser: null
            }
        }
        case AUTH_FAILED: {
            const {errorMessage} = action.payload
            return {
                ...state,
                currentUser: null,
                errorMessage,
            }
        }
        default:
            return state
    }
}
