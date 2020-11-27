import {all, call, put, takeEvery} from 'redux-saga/effects'
import {toast} from 'react-toastify'

import * as statisticAction from '../../actions/admin/statistic'
import * as statisticAPI from '../../api/admin/statistic'

function* getProjectsStatistic() {
    yield takeEvery(statisticAction.GET_PROJECTS, function* (action) {
        try {
            const statistic = yield call(statisticAPI.getProjects, action.payload)
            yield put(statisticAction.getProjectsSucceed(statistic))
        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* getUsersStatistic() {
    yield takeEvery(statisticAction.GET_USERS, function* (action) {
        try {
            const statistic = yield call(statisticAPI.getUsers, action.payload)
            yield put(statisticAction.getUsersSucceed(statistic))
        } catch (error) {
            toast.error(error.message)
        }
    })
}

export default function* saga(getState) {
    yield all([
        getProjectsStatistic(),
        getUsersStatistic()
    ])
}