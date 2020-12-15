import {all, call, fork, put, select, takeEvery} from 'redux-saga/effects'
import {toast} from 'react-toastify'
import React from "react";

import * as projectAPI from '../api/project'
import * as authAPI from '../api/auth'
import * as projectAction from '../actions/project'
import * as authAction from '../actions/auth'
import * as layoutAction from '../actions/layout'

const getUser = state => state.auth.currentUser

// FIXME このメソッドは分離する
function* fetchPublishedProject() {

    yield takeEvery(projectAction.FETCH_PUBLISHED_PROJECT, function* (action) {
        try {

            const currentUser = yield select(getUser)

            let projectBook = {};
            if (currentUser) {
                const projects = currentUser.project;
                const target = projects ? projects.find(prj => prj.ids.id === action.payload.id) : null

                if (target && target.metadata && target.metadata.sheets) {
                    projectBook = target.metadata.sheets
                } else {
                    // FIXME APIを呼び出す
                    projectBook = yield call(projectAPI.get, action.payload.id)
                }
            } else {
                // FIXME APIを呼び出す
                projectBook = yield call(projectAPI.get, action.payload.id)
            }

            yield put(projectAction.setPublishedProjectBook(projectBook))
        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* fetchDraftProject() {
    yield takeEvery(projectAction.FETCH_DRAFT_PROJECT, function* (action) {
        try {
            const projectBook = yield call(projectAPI.get, action.payload.id)
            yield put(projectAction.setDraftProjectBook(projectBook))
        } catch (error) {
            toast.error(error.message)
        }
    })
}

// TODO ここから上はデモ版なので見直す

function* createProject() {
    yield takeEvery(projectAction.CREATE_PROJECT, function* (action) {

        const currentUser = yield select(getUser)
        const { accessToken, project, role } = currentUser
        const {history, setLoading} = action.payload
        let response = yield call(projectAPI.createProject, accessToken)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.createProject, tokenResult.access_token)
            } else {
                // FIXME エラーで落とす
            }
        }

        if (response.status === 200) {
            const ids = yield response.json();
            const projectId = ids.id;

            const target = [{
                ids,
                metadata: null,
                aggregate: null,
                listdata: null,
                draftMetadata: null,
                draftFileList: null,
                draftAggregate: null,
                draftListdata: null,
                publishedDate: null,
                editing: false,
                hidden: false,
                browseTokens: []
            }]

            const targetRole = [{
                projectId,
                owner: true,
                writable: true,
                readable: true,
                expireDate: null
            }]

            const newProject = project.concat(target)
            const newRole = role.concat(targetRole)

            const newCurrentUser = {
                ...currentUser,
                project: newProject,
                role: newRole
            }

            yield put(authAction.updateCurrentUser(newCurrentUser))
            setLoading(false)
            history.push(`/me/project/${projectId}/draft/about`)
        }
    })
}

function* getProject() {
    yield takeEvery(projectAction.GET_PROJECT, function* (action) {
        const { projectId, history } = action.payload

        const response = yield call(projectAPI.getProject, projectId)

        if (response.status === 200) {
            const currentProjectView = yield response.json()
            yield put(projectAction.setCurrentProjectView(currentProjectView))
        } else {
            yield put(projectAction.setCurrentProjectView(null))

            // FIXME 動的なルーティングが必要
            if(history) {
                history.push("/404")
            }
        }
    })
}

function* listProjectMember() {
    yield takeEvery(projectAction.LIST_PROJECT_MEMBER, function* (action) {
        try {
            const projectMemberList = yield call(projectAPI.listProjectMember, action.payload.projectId)

            // TODO セットする処理
        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* inviteMemberAsEditor() {
    yield takeEvery(projectAction.INVITE_MEMBER_AS_EDITOR, function* (action) {
        try {
            yield call(projectAPI.inviteMemberAsEditor, action.payload.projectId, action.payload.accountUuid)

        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* suspendMember() {
    yield takeEvery(projectAction.SUSPEND_MEMBER, function* (action) {
        try {
            yield call(projectAPI.suspendMember, action.payload.projectId, action.payload.accountUuid)

        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* activateMember() {
    yield takeEvery(projectAction.ACTIVE_MEMBER, function* (action) {
        try {
            yield call(projectAPI.activateMember, action.payload.projectId, action.payload.accountUuid)

        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* deleteMember() {
    yield takeEvery(projectAction.DELETE_MEMBER, function* (action) {
        try {
            yield call(projectAPI.deleteMember, action.payload.projectId, action.payload.accountUuid)

        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* createAccessGrant() {
    yield takeEvery(projectAction.CREATE_ACCESS_GRANT, function* (action) {
        const currentUser = yield select(getUser)
        const {accessToken, project} = currentUser
        const { projectId, label, expireDate } = action.payload

        let response = yield call(projectAPI.createAccessGrant, accessToken, projectId, label, expireDate)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.createAccessGrant, tokenResult.access_token, projectId, label, expireDate)
            } else {
                // FIXME エラーで落とす
            }
        }

        if(response.status === 200) {
            const token = yield response.json()

            let newProject = project.map(pr => {

                if(pr.ids.id === projectId) {
                    const { browseTokens } = pr
                    let newTokens = browseTokens.slice()

                    newTokens.push({
                        label,
                        token,
                        expireDate
                    })
                    return {
                        ...pr,
                        browseTokens: newTokens
                    }
                }

                return pr
            })

            const data = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(data))
        }
    })
}

function* deleteAccessGrant() {
    yield takeEvery(projectAction.DELETE_ACCESS_GRANT, function* (action) {
        const currentUser = yield select(getUser)
        const { accessToken, project } = currentUser
        const { projectId, token } = action.payload

        let response = yield call(projectAPI.deleteAccessGrant, accessToken, token)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.deleteAccessGrant, tokenResult.access_token, token)
            } else {
                // FIXME エラーで落とす
            }
        }

        if(response.status === 200) {
            let newProject = []

            for (let pr of project) {
                if(pr.ids.id === projectId) {
                    let { browseTokens } = pr
                    browseTokens = browseTokens.filter((t) => t.token !== token)

                    pr = {
                        ...pr,
                        browseTokens
                    }
                }

                newProject.push(pr)
            }

            const data = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(data))
        }
    })
}

function* updateAccessGrant() {
    yield takeEvery(projectAction.UPDATE_ACCESS_GRANT, function* (action) {
        const currentUser = yield select(getUser)
        const {accessToken, project } = currentUser
        const { projectId, token, label, expireDate } = action.payload

        let response = yield call(projectAPI.updateAccessGrant, accessToken, token, label, expireDate)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.updateAccessGrant, tokenResult.access_token, token, label, expireDate)
            } else {
                // FIXME エラーで落とす
            }
        }

        if(response.status === 200) {
            let newProject = project.map(pr => {

                if(pr.ids.id === projectId) {
                    const { browseTokens } = pr
                    let newTokens = browseTokens.map( t => {
                        if(t.token === token) {
                            return {
                                label,
                                token,
                                expireDate
                            }
                        }

                        return t
                    })

                    return {
                        ...pr,
                        browseTokens: newTokens
                    }
                }

                return pr
            })

            const data = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(data))
        }
    })
}

function* listAccessGrant() {
    yield takeEvery(projectAction.LIST_ACCESS_GRANT, function* (action) {
        const currentUser = yield select(getUser)
        const {accessToken, project } = currentUser
        const { projectId } = action.payload

        let response = yield call(projectAPI.listAccessGrant, accessToken, projectId)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.listAccessGrant, accessToken, projectId)
            } else {
                // FIXME エラーで落とす
            }
        }

        if(response.status === 200) {
            // TODO
        }
    })
}

function* accessProjectPreviewData() {
    yield takeEvery(projectAction.ACCESS_PROJECT_VIEW_DATA, function* (action) {
        const { token, history, projectId } = action.payload

        let response = yield call(projectAPI.accessProjectPreviewData, token)
        if(response.status === 200) {
            const currentProjectView = yield response.json()

            if(currentProjectView.ids.id === projectId) {
                yield put(projectAction.setCurrentProjectView(currentProjectView))
            } else {
                history.push('/404')
            }
        } else {
            history.push('/404')
        }
    })
}

function* beginEditProject() {
    yield takeEvery(projectAction.BEGIN_EDIT_PROJECT, function* (action) {

        const currentUser = yield select(getUser)
        const {accessToken, project} = currentUser
        const {projectId} = action.payload

        let response = yield call(projectAPI.beginEditProject, accessToken, action.payload.projectId)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.beginEditProject, tokenResult.access_token, action.payload.projectId)
            } else {
                // FIXME エラーで落とす
            }
        }

        if (response.status === 200) {
            const pr = project.find(prj => prj.ids.id === projectId)
            const previewResponse = yield call(projectAPI.previewDraftMetadata, accessToken, projectId)
            const { aggregate, listdata } = yield previewResponse.json()

            const draftMetadata = {
                metadata: pr.metadata ? pr.metadata.sheets : null,
                token: null
            }

            const target = {
                ...pr,
                draftMetadata,
                editing: true,
                draftFileList: [],
                draftAggregate: aggregate,
                draftListdata: listdata
            }

            let newProject = []

            project.map(pr => {
                if (pr.ids.id === projectId) {
                    newProject.push(target)
                } else {
                    newProject.push(pr)
                }
            })

            const newCurrentUser = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(newCurrentUser))
            action.payload.history.push(`/me/project/${action.payload.projectId}/setting/edit`)
        }
    })
}

function* revertEditProject() {
    yield takeEvery(projectAction.REVERT_EDIT_PROJECT, function* (action) {
        try {
            yield call(projectAPI.revertEditProject, action.payload.projectId)

        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* getDraftMetadata() {
    yield takeEvery(projectAction.GET_DRAFT_METADATA, function* (action) {
        try {
            // TODO getDraftMetadataは何を返す？
            const draftMetadata = yield call(projectAPI.getDraftMetadata, action.payload.projectId)
        } catch (error) {
            toast.error(error.message)
        }
    })
}

function* postDraftMetadata() {
    yield takeEvery(projectAction.POST_DRAFT_METADATA, function* (action) {
        const currentUser = yield select(getUser)
        const {accessToken, project} = currentUser
        const {metadata, projectId, setIsLoading} = action.payload

        const target = project.find(prg => prg.ids.id === projectId)
        const token = target.draftMetadata.token

        const params = {
            metadata: metadata.metadata,
            token
        }

        let response = yield call(projectAPI.postDraftMetadata, accessToken, projectId, params)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.beginEditProject, tokenResult.access_token, action.payload.projectId)
            } else {
                // FIXME 多言語化対応
                const error = yield response.json()
                toast.error(error.error)

                yield put(layoutAction.setErrorKey(error.errorKey))
            }
        }

        if (response.status === 200) {
            const draftMetadata = yield response.json()
            const previewResponse = yield call(projectAPI.previewDraftMetadata, accessToken, projectId)
            const { aggregate, listdata } = yield previewResponse.json()

            let newProject = []

            project.map(prj => {
                if (prj.ids.id === projectId) {
                    const newTarget = {
                        ...prj,
                        draftMetadata,
                        draftAggregate: aggregate,
                        draftListdata: listdata,
                        editing: true
                    }

                    newProject.push(newTarget)
                } else {
                    newProject.push(prj)
                }
            })

            const newCurrentUser = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(newCurrentUser))
        } else {
            // FIXME 多言語化対応
            const error = yield response.json()
            // toast.error(error.error)

            yield put(layoutAction.setErrorKey(error.errorKey))
        }

        setIsLoading(false)
    })
}

function* previewDraftMetadata() {
    yield takeEvery(projectAction.PREVIEW_DRAFT_METADATA, function* (action) {
        const currentUser = yield select(getUser)
        const { access_token } = currentUser

        let response = yield call(projectAPI.previewDraftMetadata, access_token, action.payload.projectId)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))
                response = yield call(projectAPI.previewDraftMetadata, tokenResult.access_token, action.payload.projectId)
            } else {
                // FIXME エラーで落とす
            }
        }

        if (response.status === 200) {
            const draftMetadata = yield response.json()
            yield put(projectAction.setDraftProjectBook(draftMetadata))
        }
    })
}

function* requestPublishProject() {
    yield takeEvery(projectAction.REQUEST_PUBLISH_PROJECT, function* (action) {
        const currentUser = yield select(getUser)
        const { access_token } = currentUser

        const {projectId, setVisible, setIsLoading} = action.payload
        let response = yield call(projectAPI.requestPublishProject, access_token, projectId)

        if (response.status === 401) {
            const { accountUuid } = currentUser
            const tokenResponse = yield call(authAPI.refreshAccessToken, accountUuid)

            if(tokenResponse.status === 200) {
                const tokenResult = yield tokenResponse.json()

                const data = {
                    ...currentUser,
                    access_token: tokenResult.access_token
                }

                yield put(authAction.updateCurrentUser(data))

                response = yield call(projectAPI.requestPublishProject, tokenResult.access_token, projectId)
            } else {
                // FIXME エラーで落とす
            }
        }

        if (response.status === 200) {
            const issueNo = yield response.json()
            const { project } = currentUser
            let newProject = []

            project.map(prj => {
                if (prj.ids.id === projectId) {
                    const newTarget = {
                        ...prj,
                        issueNo
                    }

                    newProject.push(newTarget)
                } else {
                    newProject.push(prj)
                }
            })

            const data = {
                ...currentUser,
                project: newProject
            }

            yield put(authAction.updateCurrentUser(data))
        }

        setVisible(false)
        setIsLoading(false)
    })
}

function* deleteProject() {
    yield takeEvery(projectAction.DELETE_PROJECT, function* (action) {
        const currentUser = yield select(getUser)
        const { access_token } = currentUser
        yield call(projectAPI.deleteProject, access_token,action.payload.projectId)
    })
}

// TODO 画面に必要なかったら消す
function* getMetadata() {
    yield takeEvery(projectAction.GET_METADATA, function* (action) {
        try {
            const metadata = yield call(projectAPI.getMetadata, action.payload.projectId, action.payload.metadataId)

            // TODO セットする処理
        } catch (error) {
            toast.error(error.message)
        }
    })
}

export default function* saga(getState) {
    yield all([
        fork(fetchPublishedProject),
        fork(fetchDraftProject),
        // TODO ここから上はデモ版なので見直す
        fork(createProject),
        fork(getProject),
        fork(listProjectMember),
        fork(inviteMemberAsEditor),
        fork(suspendMember),
        fork(activateMember),
        fork(deleteMember),
        fork(createAccessGrant),
        fork(deleteAccessGrant),
        fork(updateAccessGrant),
        fork(listAccessGrant),
        fork(accessProjectPreviewData),
        fork(beginEditProject),
        fork(revertEditProject),
        fork(getDraftMetadata),
        fork(postDraftMetadata),
        fork(previewDraftMetadata),
        fork(requestPublishProject),
        fork(deleteProject),
        // TODO 画面に必要なかったら消す
        fork(getMetadata),
    ])
}