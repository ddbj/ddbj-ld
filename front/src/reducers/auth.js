import {
    AUTH_FAILED,
    SIGN_IN_SUCEED,
    SIGN_IN_WITH_DDBJ,
    SIGN_IN_WITH_METABOBANK,
    SIGN_OUT_SUCCEED,
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
        case SIGN_IN_WITH_METABOBANK:
        case SIGN_IN_WITH_DDBJ: {
            return {
                ...state,
                errorMessage: null
            }
        }
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
        case SIGN_IN_SUCEED: {
            const {currentUser} = action.payload
            return {
                ...state,
                currentUser
            }
        }
        case SIGN_OUT_SUCCEED: {
            return {
                ...state,
                // FIXME 消すのはこれだけではない…はず
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
