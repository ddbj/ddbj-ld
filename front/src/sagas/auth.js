import {all, call, fork, put, select, takeEvery} from 'redux-saga/effects'

import * as authAPI from '../api/auth'
import * as authAction from '../actions/auth'

const getUser = state => state.auth.currentUser;

function* signIn() {
    yield takeEvery(authAction.SIGN_IN, function* (action) {
        const {code, history} = action.payload
        const response = yield call(authAPI.signIn, code)

        if (response.status === 200) {
            const currentUser = yield response.json()
            yield put(authAction.signInSucceed(currentUser))
        } else {
            history.push(`/401`)
        }
    })
}

function* signInWithJVar() {
    yield takeEvery(authAction.SIGN_IN_WITH_METABOBANK, function* (action) {
        try {
            const {id, password} = action.payload
            const currentUser = yield call(authAPI.signInWithJVar, id, password)
            yield put(authAction.signInSucceed(currentUser))
        } catch (error) {
            yield put(authAction.authFailed(error.message))
            throw error
        }
    })
}

function* signInWithDDBJ() {
    yield takeEvery(authAction.SIGN_IN_WITH_DDBJ, function* (action) {
        try {
            const currentUser = yield call(authAPI.signInWithDDBJ)
            yield put(authAction.signInSucceed(currentUser))
        } catch (error) {
            yield put(authAction.authFailed(error.message))
            throw error
        }
    })
}

function* signOut() {
    yield takeEvery(authAction.SIGN_OUT, function* (action) {
        try {
            yield call(authAPI.signOut)
            yield put(authAction.signOutSucceed())
        } catch (error) {
            yield put(authAction.authFailed(error.message))
            throw error
        }
    })
}

function* updateAccessToken() {
    yield takeEvery(authAction.UPDATE_ACCESS_TOKEN, function* (action) {
        const currentUser = yield select(getUser)
        const {accountUuid} = currentUser
        const response = yield call(authAPI.updateAccessToken, accountUuid)

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
        fork(signIn),
        fork(signInWithJVar),
        fork(signInWithDDBJ),
        fork(signOut),
        fork(updateAccessToken)
    ])
}
