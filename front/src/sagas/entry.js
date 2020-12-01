import {all, call, fork, put, select, takeEvery} from 'redux-saga/effects'
import React from "react";

import * as entryAPI from '../api/entry'
import * as authAPI from '../api/auth'
import * as entryAction from '../actions/entry'
import * as authAction from '../actions/auth'

const getUser         = state => state.auth.currentUser

function* createEntry() {
    yield takeEvery(entryAction.CREATE_ENTRY, function* (action) {

        const currentUser = yield select(getUser)
        const { access_token } = currentUser
        const { history, title, description, setLoading } = action.payload
        let response = yield call(entryAPI.createEntry, access_token, title, description)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenInfo.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.createEntry, tokenInfo.access_token, title, description)
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 201) {
            const entry = yield response.json()

            history.push(`/entries/jvar/${entry.uuid}`)
            setLoading(false)
        }
    })
}

function* getEntries() {
    yield takeEvery(entryAction.GET_ENTRIES, function* (action) {
        const currentUser = yield select(getUser)
        const { access_token } = currentUser
        const { history } = action.payload

        let response = yield call(entryAPI.getEntries, access_token)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenInfo.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.getEntries, tokenInfo.access_token)
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
        const { access_token } = currentUser
        const { history, uuid, setLoading } = action.payload
        let response = yield call(entryAPI.deleteEntry, access_token, uuid)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenInfo.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.deleteEntry, access_token, uuid)
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 200) {
            history.push("/entries/jvar")
            setLoading(false)
        }
    })
}

function* getEntryInformation() {
    yield takeEvery(entryAction.GET_ENTRY_INFORMATION, function* (action) {

        const currentUser = yield select(getUser)
        const { access_token } = currentUser
        const { history, uuid, setLoading } = action.payload
        let response = yield call(entryAPI.getEntryInformation, access_token, uuid)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenInfo.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.getEntryInformation, access_token, uuid)
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 200) {
            const currentEntry = yield response.json()
            yield put(entryAction.setCurrentEntry(currentEntry))
        }

        if (response.status === 404) {
            history.push("/404")
        }

        setLoading(false)
    })
}

function* postComment() {
    yield takeEvery(entryAction.POST_COMMENT, function* (action) {

        const currentUser = yield select(getUser)
        const { access_token } = currentUser
        const { history, entryUUID, comment, setLoading } = action.payload

        let response = yield call(entryAPI.postComment, access_token, entryUUID, comment)

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenInfo.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield yield call(entryAPI.postComment, tokenInfo.access_token, entryUUID, comment)
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 201) {
            history.push(`/entries/jvar/${entryUUID}/comments`)
            setLoading(false)
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(createEntry),
        fork(getEntries),
        fork(deleteEntry),
        fork(getEntryInformation),
        fork(postComment)
    ])
}