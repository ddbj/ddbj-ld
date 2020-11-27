import {Forbidden, NotFound} from './errors'

import {requestDelete, requestGet, requestPost} from './common'
import config from '../config'
// TODO: プロトタイプ用のため削除
import {getProjectBookById} from './data'

// TODO: 内部のロジックはプロトタイプ用のため削除
export const get = async id => {
    if (id === 'RMM99999') {
        throw Forbidden
    } else if (id === 'RMM00000') {
        throw NotFound
    }

    const projectBook = getProjectBookById(id)
    return projectBook
}

const createProject = (accessToken) => {
    return requestPost(accessToken, config.createProjectApi, null)
}

const getProject = (projectId) => {
    const url = config.getProjectApi.replace("{project-id}", projectId)
    return requestGet(null, url)
}

const listProjectMember = (accessToken, projectId) => {

}

const inviteMemberAsEditor = (accessToken, projectId, accountUuid) => {

}

const suspendMember = (accessToken, projectId, accountUuid) => {

}

const activateMember = (accessToken, projectId, accountUuid) => {

}

const deleteMember = (accessToken, projectId, accountUuid) => {

}

const createAccessGrant = (accessToken, projectId, label, expireDate) => {
    const url = config.createAccessGrantApi.replace("{project-id}", projectId)
    const params = {
        label,
        expireDate
    }

    return requestPost(accessToken, url, params)
}

const deleteAccessGrant = (accessToken, token) => {
    const url = config.deleteAccessGrantApi.replace("{token}", token)

    return requestDelete(accessToken, url)
}

const updateAccessGrant = (accessToken, token, label, expireDate) => {
    const url = config.updateAccessGrantApi.replace("{token}", token)
    const params = {
        label,
        expireDate
    }

    return requestPost(accessToken, url, params)
}

const listAccessGrant = (accessToken, projectId) => {
    const url = config.listAccessGrantApi.replace("{project-id}", projectId)

    return requestGet(accessToken, url)
}

const accessProjectPreviewData = (token) => {
    const url = config.accessProjectPreviewDataApi.replace("{token}", token)

    return requestGet(null, url)
}

const beginEditProject = (accessToken, projectId) => {
    const url = config.beginEditProjectApi.replace("{project-id}", projectId)

    return requestPost(accessToken, url, null)
}

const revertEditProject = (accessToken, projectId) => {

}

const getDraftMetadata = (accessToken, projectId) => {

}

const postDraftMetadata = (accessToken, projectId, params) => {
    const url = config.postDraftMetadataApi.replace("{project-id}", projectId)

    return requestPost(accessToken, url, params)
}

const previewDraftMetadata = (accessToken, projectId) => {
    const url = config.previewDraftMetadataApi.replace("{project-id}", projectId)

    return requestGet(accessToken, url)
}

const requestPublishProject = (accessToken, projectId) => {
    const url = config.requestPublishProjectApi.replace("{project-id}", projectId)

    return requestPost(accessToken, url, null)
}

const deleteProject = (accessToken, projectId) => {

}

// TODO 画面に必要なかったら消す
const getMetadata = (projectId, metadataId) => {

}

export {
    createProject,
    getProject,
    listProjectMember,
    inviteMemberAsEditor,
    suspendMember,
    activateMember,
    deleteMember,
    createAccessGrant,
    deleteAccessGrant,
    updateAccessGrant,
    listAccessGrant,
    accessProjectPreviewData,
    beginEditProject,
    revertEditProject,
    getDraftMetadata,
    postDraftMetadata,
    previewDraftMetadata,
    requestPublishProject,
    deleteProject,
    getMetadata
}