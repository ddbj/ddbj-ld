import {all} from 'redux-saga/effects'

import projectsSaga from './project'
import statisticSaga from './statistic'

export default function* saga(getState) {
    yield all([
        statisticSaga(),
        projectsSaga()
    ]);
};