export const SIGN_IN = 'AUTH/SIGN_IN'

export const signIn = (code, history) => ({
    type: SIGN_IN,
    payload: {code, history}
})

export const SIGN_IN_WITH_METABOBANK = 'AUTH/SIGN_IN_WITH_METABOBANK'

export const signInWithJVar = (id, password) => ({
    type: SIGN_IN_WITH_METABOBANK,
    payload: {id, password}
})

export const SIGN_IN_WITH_DDBJ = 'AUTH/SIGN_IN_WITH_DDBJ'

export const signInWithDDBJ = () => ({
    type: SIGN_IN_WITH_DDBJ,
})

export const SIGN_IN_SUCEED = 'AUTH/SIGN_IN_SUCCEED'

export const signInSucceed = currentUser => ({
    type: SIGN_IN_SUCEED,
    payload: {currentUser}
})

export const AUTH_FAILED = 'AUTH/FAILED'

export const authFailed = errorMessage => ({
    type: AUTH_FAILED,
    payload: {
        errorMessage
    }
})

export const SIGN_OUT = 'AUTH/SIGN_OUT'

export const signOut = () => ({
    type: SIGN_OUT
})

export const SIGN_OUT_SUCCEED = 'AUTH/SIGN_OUT_SUCCEED'

export const signOutSucceed = () => ({
    type: SIGN_OUT_SUCCEED
})

export const UPDATE_CURRENT_USER = 'AUTH/UPDATE_CURRENT_USER'

export const updateCurrentUser = data => ({
    type: UPDATE_CURRENT_USER,
    payload: {data}
})

export const UPDATE_ACCESS_TOKEN = 'AUTH/UPDATE_ACCESS_TOKEN'

export const updateAccessToken = () => ({
    type: UPDATE_ACCESS_TOKEN
})