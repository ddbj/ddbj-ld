import {all, call, fork, takeEvery} from 'redux-saga/effects'
import {toast} from 'react-toastify'

import * as userApi from '../api/user'
import * as userAction from '../actions/user'

function* inviteProject() {
    yield takeEvery(userAction.INVITE_PROJECT, function* (action) {
        try {
            yield call(userApi.inviteProject, action.payload.projectId, action.payload.targets)

        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* updateProfile() {
    yield takeEvery(userAction.UPDATE_PROFILE, function* (action) {
        try {
            yield call(userApi.updateProfile, action.payload.accountUuid, action.payload.profile)

        } catch (error) {
            toast.error(error.message)
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(inviteProject),
        fork(updateProfile),
    ])
}