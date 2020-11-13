import {all, call, fork, put, select, takeEvery} from 'redux-saga/effects'

import * as authAPI from '../api/auth'
import * as authAction from '../actions/auth'

const getUser = state => state.auth.currentUser;

function* login() {
    yield takeEvery(authAction.LOG_IN, function* (action) {
        const {code, history} = action.payload
        const response = yield call(authAPI.login, code)

        if (response.status === 200) {
            const currentUser = yield response.json()
            yield put(authAction.loginSucceed(currentUser))
        } else {
            history.push(`/401`)
        }
    })
}

function* logOut() {
    yield takeEvery(authAction.LOG_OUT, function* (action) {
        try {
            yield call(authAPI.logOut)
            yield put(authAction.logOutSucceed())
        } catch (error) {
            yield put(authAction.authFailed(error.message))
            throw error
        }
    })
}

function* refreshAccessToken() {
    yield takeEvery(authAction.REFRESH_ACCESS_TOKEN, function* (action) {
        const currentUser = yield select(getUser)
        const {accountUuid} = currentUser
        const response = yield call(authAPI.refreshAccessToken, accountUuid)

        if (response.status === 200) {
            const {accessToken} = yield response.json()
            const data = {
                ...currentUser,
                accessToken
            }

            yield put(authAction.updateCurrentUser(data))
        } else {
            yield put(authAction.authFailed("もう一度ログインし直してください。"))
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(login),
        fork(logOut),
        fork(refreshAccessToken)
    ])
}
