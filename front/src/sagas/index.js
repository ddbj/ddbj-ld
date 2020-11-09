import {all} from 'redux-saga/effects';

import searchSaga from './search'
import projectSaga from './project'
import authSaga from './auth'
import adminSaga from './admin'
import meSaga from './me'
import accountSaga from './account'
import userSaga from './user'
import fileSaga from './file'

export default function* saga(getState) {
    yield all([
        meSaga(),
        authSaga(),
        adminSaga(),
        searchSaga(),
        projectSaga(),
        accountSaga(),
        userSaga(),
        fileSaga(),
    ]);
};