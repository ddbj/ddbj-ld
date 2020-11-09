import {all, call, fork, put, select, takeEvery} from 'redux-saga/effects'
import {toast} from 'react-toastify'
import FileSaver from 'file-saver'

import * as fileApi from '../api/file'
import * as fileAction from '../actions/file'
import * as authAction from "../actions/auth"
import * as authAPI from "../api/auth";
import * as projectAPI from "../api/project";

const getUser = state => state.auth.currentUser

function* getProjectFile() {
    yield takeEvery(fileAction.GET_PROJECT_FILE, function* (action) {
        try {
            const {projectId} = action.payload

            const projectFile = yield call(fileApi.getProjectFile, projectId)

            // TODO セットする処理
        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* upload() {
    yield takeEvery(fileAction.UPLOAD, function* (action) {
        const {projectId, type, name, file, setIsLoading} = action.payload

        const currentUser = yield select(getUser)
        const {accessToken, project} = currentUser

        let preUploadResponse = yield call(fileApi.preUpload, accessToken, projectId, type, name)

        if (preUploadResponse.status === 403) {
            // FIXME ファイルアップロード時のエラー
        }

        if (preUploadResponse.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.updateAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    accessToken: tokenResult.accessToken
                }

                yield put(authAction.updateCurrentUser(data))

                preUploadResponse = yield call(fileApi.preUpload, tokenResult.accessToken, projectId, type, name)
            } else {
                // FIXME エラーで落とす
            }
        }

        const {uploadToken} = yield preUploadResponse.json();

        let body = new FormData()
        body.append("files", file, name)

        const response = yield call(fileApi.upload, projectId, type, name, body, uploadToken)

        if (response.status === 200) {
            const fileInfo = yield response.json()
            const targetPrj = project.find(pr => pr.ids.id === projectId)
            const {draftFileList} = targetPrj
            const targetDraftFile = draftFileList.find(draftFile => draftFile.type === type && draftFile.name === name)

            // 更新でなく登録だった場合、ドラフトファイルに追加
            const newDraftFileList = targetDraftFile
                ? draftFileList
                : draftFileList.concat(fileInfo)

            const newTargetPrj = {
                ...targetPrj,
                draftFileList: newDraftFileList
            }

            const newProject = project.map((prj) => {
                if (prj.ids.id === projectId) {
                    return newTargetPrj
                }

                return prj
            })

            const newCurrentUser = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(newCurrentUser))
            setIsLoading(false)
        }
    })
}

// 未公開・非公開用のアクション、公開はアドレスを直に叩く
function* downLoad() {
    yield takeEvery(fileAction.DOWNLOAD, function* (action) {
        const {projectId, type, name, setIsDownloadLoading} = action.payload
        const currentUser = yield select(getUser)
        const {accessToken} = currentUser

        let response = yield call(fileApi.download, accessToken, projectId, type, name)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.updateAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    accessToken: tokenResult.accessToken
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(fileApi.download, tokenResult.accessToken, projectId, type, name)
            } else {
                // FIXME エラーで落とす
            }
        }

        if (response.status === 200) {
            const file = yield response.blob()
            FileSaver.saveAs(file, name)
            setIsDownloadLoading(false)
        }
    })
}

function* deleteFile() {
    yield takeEvery(fileAction.DELETE, function* (action) {
        const {projectId, type, name, setIsDeleteLoading} = action.payload
        const currentUser = yield select(getUser)
        const {accessToken, project} = currentUser

        let response = yield call(fileApi.deleteFile, accessToken, projectId, type, name)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.updateAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    accessToken: tokenResult.accessToken
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(fileApi.deleteFile, accessToken, projectId, type, name)
            } else {
                // FIXME エラーで落とす
            }
        }

        if (response.status === 200) {
            const newProject = project.map((prj) => {
                if (prj.ids.id === projectId) {
                    const newDraftFileList = prj.draftFileList.filter((file) => file.name != name || file.type != type)

                    return {
                        ...prj,
                        draftFileList: newDraftFileList
                    }
                }

                return prj
            })

            const newCurrentUser = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(newCurrentUser))
            setIsDeleteLoading(false)
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(getProjectFile),
        fork(upload),
        fork(downLoad),
        fork(deleteFile),
    ])
}