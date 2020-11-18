import {all, call, fork, put, select, takeEvery} from 'redux-saga/effects'
import React from "react";

import * as entryAPI from '../api/entry'
import * as authAPI from '../api/auth'
import * as entryAction from '../actions/entry'
import * as authAction from '../actions/auth'

const getUser         = state => state.auth.currentUser
const getStateEntries = state => state.entry.entries

function* createEntry() {
    yield takeEvery(entryAction.CREATE_ENTRY, function* (action) {

        const currentUser = yield select(getUser)
        const { accessToken } = currentUser
        const { history, title, description } = action.payload
        let response = yield call(entryAPI.createEntry, accessToken, title, description)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    accessToken: tokenInfo.accessToken
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.createEntry, tokenInfo.accessToken, title, description)
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 200) {
            const entry   = yield response.json()
            let entries   = yield select(getStateEntries)

            entries.push(entry)

            yield put(entryAction.setEntries(entries))
        }
    })
}

function* getEntries() {
    yield takeEvery(entryAction.GET_ENTRIES, function* (action) {
        const currentUser = yield select(getUser)
        const { accessToken } = currentUser
        const { history } = action.payload

        let response = yield call(entryAPI.getEntries, accessToken)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    accessToken: tokenInfo.accessToken
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.getEntries, tokenInfo.accessToken)
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 200) {
            const entries = yield response.json()

            yield put(entryAction.setEntries(entries))
        }
    })
}

function* deleteEntry() {
    yield takeEvery(entryAction.DELETE_ENTRY, function* (action) {

        const currentUser = yield select(getUser)
        const { accessToken } = currentUser
        const { history, uuid } = action.payload
        let response = yield call(entryAPI.deleteEntry, accessToken, uuid)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    accessToken: tokenInfo.accessToken
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.deleteEntry, accessToken, uuid)
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 200) {
            const entries = yield select(getStateEntries)
            const newEntries = entries.filter((entry) => entry.uuid !== uuid)

            yield put(entryAction.setEntries(newEntries))
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(createEntry),
        fork(getEntries),
        fork(deleteEntry)
    ])
}