<<<<<<< HEAD
import React from "react"
import { all, call, fork, put, select, takeEvery } from 'redux-saga/effects'
import FileSaver from 'file-saver'
import { toast } from "react-toastify"
=======
import {all, call, fork, put, select, takeEvery} from 'redux-saga/effects'
import React from "react";
>>>>>>> 差分修正

import * as entryAPI from '../api/entry'
import * as authAPI from '../api/auth'
import * as entryAction from '../actions/entry'
import * as authAction from '../actions/auth'

<<<<<<< HEAD
const getUser = state => state.auth.currentUser
=======
const getUser         = state => state.auth.currentUser
const getStateEntries = state => state.entry.entries
>>>>>>> 差分修正

function* createEntry() {
    yield takeEvery(entryAction.CREATE_ENTRY, function* (action) {

        const currentUser = yield select(getUser)
<<<<<<< HEAD
        let { access_token } = currentUser
        const { history, type, setLoading } = action.payload
        let response = yield call(entryAPI.createEntry, access_token, type)
=======
        const { accessToken } = currentUser
        const { history, title, description } = action.payload
        let response = yield call(entryAPI.createEntry, accessToken, title, description)
>>>>>>> 差分修正

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
<<<<<<< HEAD
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
=======

                const data = {
                    ...currentUser,
                    accessToken: tokenInfo.accessToken
>>>>>>> 差分修正
                }

                yield put(authAction.updateCurrentUser(data))

<<<<<<< HEAD
                response = yield call(entryAPI.createEntry, access_token, type)
=======
                response = yield call(entryAPI.createEntry, tokenInfo.accessToken, title, description)
>>>>>>> 差分修正
            } else {
                history.push(`/401`)
            }
        }

<<<<<<< HEAD
        if (response.status === 201) {
            const entry = yield response.json()

            history.push(`/entries/jvar/${entry.uuid}`)
            setLoading(false)
=======
        if (response.status === 200) {
            const entry   = yield response.json()
            let entries   = yield select(getStateEntries)

            entries.push(entry)

            yield put(entryAction.setEntries(entries))
>>>>>>> 差分修正
        }
    })
}

function* getEntries() {
    yield takeEvery(entryAction.GET_ENTRIES, function* (action) {
        const currentUser = yield select(getUser)
<<<<<<< HEAD
        let { access_token } = currentUser
        const { history, setLoading } = action.payload

        let response = yield call(entryAPI.getEntries, access_token)
=======
        const { accessToken } = currentUser
        const { history } = action.payload

        let response = yield call(entryAPI.getEntries, accessToken)
>>>>>>> 差分修正

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
<<<<<<< HEAD
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
=======

                const data = {
                    ...currentUser,
                    accessToken: tokenInfo.accessToken
>>>>>>> 差分修正
                }

                yield put(authAction.updateCurrentUser(data))

<<<<<<< HEAD
                response = yield call(entryAPI.getEntries, access_token)
=======
                response = yield call(entryAPI.getEntries, tokenInfo.accessToken)
>>>>>>> 差分修正
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 200) {
            const entries = yield response.json()

            yield put(entryAction.setEntries(entries))
        }
<<<<<<< HEAD

        setLoading(false)
=======
>>>>>>> 差分修正
    })
}

function* deleteEntry() {
    yield takeEvery(entryAction.DELETE_ENTRY, function* (action) {

        const currentUser = yield select(getUser)
<<<<<<< HEAD
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, uuid, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.deleteEntry, access_token, entryUUID)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push("/entries/jvar")
        setLoading(false)
    })
}

function* getEntryInfo() {
    yield takeEvery(entryAction.GET_ENTRY_INFO, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, uuid, setLoading } = action.payload
        let response = yield call(entryAPI.getEntryInfo, access_token, uuid)
=======
        const { accessToken } = currentUser
        const { history, uuid } = action.payload
        let response = yield call(entryAPI.deleteEntry, accessToken, uuid)
>>>>>>> 差分修正

        if (response.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
<<<<<<< HEAD
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
=======

                const data = {
                    ...currentUser,
                    accessToken: tokenInfo.accessToken
>>>>>>> 差分修正
                }

                yield put(authAction.updateCurrentUser(data))

<<<<<<< HEAD
                response = yield call(entryAPI.getEntryInfo, access_token, uuid)
            } else {
                history.push("/401")
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
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, comment, curator, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token    = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.postComment, access_token, entryUUID, comment, curator)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}/comments`)
        setLoading(false)
    })
}

function* editComment() {
    yield takeEvery(entryAction.EDIT_COMMENT, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, commentUUID, comment, curator, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.editComment, access_token, entryUUID, commentUUID, comment, curator)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}/comments`)
        setLoading(false)
    })
}

function* deleteComment() {
    yield takeEvery(entryAction.DELETE_COMMENT, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, commentUUID, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.deleteComment, access_token, entryUUID, commentUUID)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}/comments`)
        setLoading(false)
    })
}

function* updateFile() {
    yield takeEvery(entryAction.UPDATE_FILE, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, type, name, file } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            const uploadTokenResponse = yield call(entryAPI.getUploadToken, access_token, entryUUID, type, name)

            if (uploadTokenResponse.status === 200) {
                const result = yield uploadTokenResponse.json()
                const uploadToken = result.token;

                let body = new FormData()
                body.append("file", file, name)

                const response = yield call(entryAPI.uploadFile, access_token, entryUUID, type, name, uploadToken, body)

                if(response.status == 200) {
                    toast.success("Upload is successful!")
                    history.push(`/entries/jvar/${entryUUID}`)
                } else {
                    history.push(`/entries/jvar/${entryUUID}/files/error`)
                }
            }
        }
    })
}

function* downloadFile() {
    yield takeEvery(entryAction.DOWNLOAD_FILE, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const {history, entryUUID, type, name} = action.payload

        let response = yield call(entryAPI.downloadFile, access_token, entryUUID, type, name)

        if (response.status === 401) {
            const {uuid} = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if (tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(entryAPI.downloadFile, access_token, entryUUID, type, name)
=======
                response = yield call(entryAPI.deleteEntry, accessToken, uuid)
>>>>>>> 差分修正
            } else {
                history.push(`/401`)
            }
        }

        if (response.status === 200) {
<<<<<<< HEAD
            const file = yield response.blob()
            FileSaver.saveAs(file, name)
        } else {
            history.push(`/401`)
        }
    })
}

function* validateMetadata() {
    yield takeEvery(entryAction.VALIDATE_METADATA, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            const response = yield call(entryAPI.validateMetadata, access_token, entryUUID)

            if (response.status === 200) {
                history.push(`/entries/jvar/${entryUUID}`)
                toast.success("Validation is successful!")
            }
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}`)
        setLoading(false)
    })
}

function* submitEntry() {
    yield takeEvery(entryAction.SUBMIT_ENTRY, function* (action) {
        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            const response = yield call(entryAPI.submitEntry, access_token, entryUUID)

            if (response.status === 200) {
                toast.success("Submit is successful!")
            } else {
                toast.error("Submit is failed")
            }
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}`)
        setLoading(false)
    })
}

function* deleteFile() {
    yield takeEvery(entryAction.DELETE_FILE, function* (action) {
        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, fileType, fileName, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            const response = yield call(entryAPI.deleteFile, access_token, entryUUID, fileType, fileName)

            if (response.status === 200) {
                toast.success("Delete is successful!")
            } else {
                toast.error("Delete is failed")
            }
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}`)
        setLoading(false)
    })
}

function* createRequest() {
    yield takeEvery(entryAction.CREATE_REQUEST, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, type, comment, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token    = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.createRequest, access_token, entryUUID, type, comment)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}/requests`)
        setLoading(false)
    })
}

function* editRequest() {
    yield takeEvery(entryAction.EDIT_REQUEST, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, requestUUID, comment, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token    = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.editRequest, access_token, entryUUID, requestUUID, comment)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}/requests`)
        setLoading(false)
    })
}

function* cancelRequest() {
    yield takeEvery(entryAction.CANCEL_REQUEST, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, requestUUID, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token    = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.cancelRequest, access_token, entryUUID, requestUUID)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}/requests`)
        setLoading(false)
    })
}

function* applyRequest() {
    yield takeEvery(entryAction.APPLY_REQUEST, function* (action) {

        const currentUser = yield select(getUser)
        let { access_token } = currentUser
        const { history, entryUUID, updateToken, requestUUID, setLoading } = action.payload

        let checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)

        if (checkTokenResponse.status === 401) {
            const { uuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, uuid)

            if(tokenResponse.status === 200) {
                const tokenInfo = yield tokenResponse.json()
                access_token    = tokenInfo.access_token

                const data = {
                    ...currentUser,
                    access_token
                }

                yield put(authAction.updateCurrentUser(data))

                checkTokenResponse = yield call(entryAPI.checkUpdateToken, access_token, entryUUID, updateToken)
            } else {
                history.push(`/401`)
            }
        }

        if(checkTokenResponse.status === 200) {
            yield call(entryAPI.applyRequest, access_token, entryUUID, requestUUID)
        } else {
            toast.error("This entry is old. Please refresh")
        }

        history.push(`/entries/jvar/${entryUUID}/requests`)
        setLoading(false)
=======
            const entries = yield select(getStateEntries)
            const newEntries = entries.filter((entry) => entry.uuid !== uuid)

            yield put(entryAction.setEntries(newEntries))
        }
>>>>>>> 差分修正
    })
}

export default function* saga(getState) {
    yield all([
        fork(createEntry),
        fork(getEntries),
<<<<<<< HEAD
        fork(deleteEntry),
        fork(getEntryInfo),
        fork(postComment),
        fork(editComment),
        fork(deleteComment),
        fork(updateFile),
        fork(downloadFile),
        fork(validateMetadata),
        fork(submitEntry),
        fork(deleteFile),
        fork(createRequest),
        fork(editRequest),
        fork(cancelRequest),
        fork(applyRequest),
    ])
}
=======
        fork(deleteEntry)
    ])
}
>>>>>>> 差分修正
