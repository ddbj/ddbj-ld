import {all, call, fork, put, takeEvery} from 'redux-saga/effects'
import {toast} from 'react-toastify'

import * as meAPI from '../api/me'

import * as meAction from '../actions/me'
import * as authAction from '../actions/auth'

function* fetchProjects() {
    yield takeEvery(meAction.FETCH_PROJECTS, function* (action) {
        yield put(meAction.fetchProjectsStart())
        try {
            const {rows, total} = yield call(meAPI.getProjects, action.payload)
            yield put(meAction.setProjects({rows, total}))
        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* updateProfile() {
    yield takeEvery(meAction.UPDATE_PROFILE, function* (action) {
        try {
            const data = yield call(meAPI.updateProfile, action.payload)
            yield put(authAction.updateCurrentUser(data))
            toast.success('プロフィールを更新しました')
        } catch (error) {
            toast.error(error.message)
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(fetchProjects),
        fork(updateProfile)
    ]);
};