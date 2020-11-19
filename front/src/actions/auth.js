export const LOG_IN = 'AUTH/LOG_IN'

export const login = (code, history) => ({
    type: LOG_IN,
    payload: {code, history}
})

export const LOG_IN_SUCCEED = 'AUTH/LOG_IN_SUCCEED'

export const loginSucceed = currentUser => ({
    type: LOG_IN_SUCCEED,
    payload: {currentUser}
})

export const AUTH_FAILED = 'AUTH/FAILED'

export const authFailed = errorMessage => ({
    type: AUTH_FAILED,
    payload: {
        errorMessage
    }
})

export const LOG_OUT = 'AUTH/LOG_OUT'

export const logOut = () => ({
    type: LOG_OUT
})

export const LOG_OUT_SUCCEED = 'AUTH/LOG_OUT_SUCCEED'

export const logOutSucceed = () => ({
    type: LOG_OUT_SUCCEED
})

export const UPDATE_CURRENT_USER = 'AUTH/UPDATE_CURRENT_USER'

export const updateCurrentUser = data => ({
    type: UPDATE_CURRENT_USER,
    payload: {data}
})

export const REFRESH_ACCESS_TOKEN = 'AUTH/REFRESH_ACCESS_TOKEN'

export const refreshAccessToken = () => ({
    type: REFRESH_ACCESS_TOKEN
})