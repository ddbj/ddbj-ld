import {all, call, fork, put, takeEvery} from 'redux-saga/effects'
import {toast} from 'react-toastify'

import * as searchAPI from '../api/search'
import * as searchAction from '../actions/search'

function* search() {
    yield takeEvery(searchAction.SEARCH, function* (action) {
        const {keyword, samples, instruments, offset, count, operation} = action.payload


        yield put(searchAction.searchStart())

        try {
            const response = yield call(searchAPI.search, keyword, samples, instruments, offset, count, operation)

            if (response.status == 200) {
                const searchResult = yield response.json()

                const hits = searchResult.hits.hits
                const rows = hits.map(hit => hit._source)
                const total = searchResult.hits.total.value

                yield put(searchAction.searchSucceed({rows, total}))
            } else {
                // FIXME エラー処理
            }
        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* getSetting() {
    yield takeEvery(searchAction.GET_SETTING, function* (action) {
        const response = yield call(searchAPI.getSetting)

        if (response.status == 200) {
            const setting = yield response.json()

            yield put(searchAction.settingSucceed({setting}))
        } else {
            // FIXME エラー処理
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(search),
        fork(getSetting)
    ]);
};