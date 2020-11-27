import {all, call, put, takeEvery} from 'redux-saga/effects'
import {toast} from 'react-toastify'

import * as projectAPI from '../../api/admin/project'
import * as projectAction from '../../actions/admin/project'

function* getAllProjects() {
    yield takeEvery(projectAction.GET_ALL_PROJECTS, function* (action) {
        yield put(projectAction.getAllProjectsStart())
        try {
            const {rows, total} = yield call(projectAPI.getAllProjects, action.payload)
            yield put(projectAction.getAllProjectsSucceed({rows, total}))
        } catch (error) {
            toast.error(error.message)
        }
    })
}

export default function* saga(getState) {
    yield all([
        getAllProjects()
    ]);
};