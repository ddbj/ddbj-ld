import config from "../config";
import { requestDelete, requestGet, requestPost } from "./common";

const getEntries = (accessToken) => {
    const url = config.getEntriesApi
    return requestGet(accessToken, url)
}

const createEntry = (accessToken, type) => {
    const url = config.createEntryApi
    const params = {
        type
    }

    return requestPost(accessToken, url, params)
}

const deleteEntry = (accessToken, entryUUID) => {
    const url = config.deleteEntryApi.replace("{entry_uuid}", entryUUID)

    return requestDelete(accessToken, url)
}

const getEntryInfo = (accessToken, entryUUID) => {
    const url = config.getEntryInfoApi.replace("{entry_uuid}", entryUUID)

    return requestGet(accessToken, url)
}

const postComment = (accessToken, entryUUID, comment, curator) => {
    const url = config.postCommentApi.replace("{entry_uuid}", entryUUID)
    const params = {
        comment,
        curator
    }

    return requestPost(accessToken, url, params)
}

const editComment = (accessToken, entryUUID, commentUUID, comment, curator) => {
    const url = config.editCommentApi.replace("{entry_uuid}", entryUUID).replace("{comment_uuid}", commentUUID)
    const params = {
        comment,
        curator
    }

    return requestPost(accessToken, url, params)
}

const deleteComment = (accessToken, entryUUID, commentUUID) => {
    const url = config.editCommentApi.replace("{entry_uuid}", entryUUID).replace("{comment_uuid}", commentUUID)

    return requestDelete(accessToken, url)
}

const getUploadToken = (accessToken, entryUUID, type, name) => {
    const url = config.getUploadTokenApi
        .replace("{entry_uuid}", entryUUID)
        .replace("{file_type}", type)
        .replace("{file_name}", name)

    return requestGet(accessToken, url)
}

const uploadFile = (accessToken, entryUUID, type, name, token, body) => {
    const method = "POST"

    const url = config.uploadFileApi
        .replace("{entry_uuid}", entryUUID)
        .replace("{file_type}", type)
        .replace("{file_name}", name)
        .replace("{upload_token}", token)

    const mode = 'cors'

    return fetch(url
        , {method, body, mode})
        .then(response => {
            return response
        })
        .catch(error => ({error}))
}

const downloadFile = (accessToken, entryUUID, type, name) => {
    const url = config.downloadFileApi
        .replace("{entry_uuid}", entryUUID)
        .replace("{file_type}", type)
        .replace("{file_name}", name)

    return requestGet(accessToken, url)
}

const validateMetadata = (accessToken, entryUUID) => {
    const url = config.validateMetadataApi
        .replace("{entry_uuid}", entryUUID)

    return requestPost(accessToken, url, null)
}

const submitEntry = (accessToken, entryUUID) => {
    const url = config.submitEntryApi
        .replace("{entry_uuid}", entryUUID)

    return requestPost(accessToken, url, null)
}

const deleteFile  = (accessToken, entryUUID, fileType, fileName) => {
    const url = config.deleteFileApi
        .replace("{entry_uuid}", entryUUID)
        .replace("{file_type}", fileType)
        .replace("{file_name}", fileName)

    return requestDelete(accessToken, url)
}

const checkUpdateToken  = (accessToken, entryUUID, updateToken) => {
    const url = config.checkUpdateTokenApi
        .replace("{entry_uuid}", entryUUID)
        .replace("{update_token}", updateToken)

    return requestGet(accessToken, url)
}

export {
    getEntries,
    createEntry,
    deleteEntry,
    getEntryInfo,
    postComment,
    editComment,
    deleteComment,
    getUploadToken,
    uploadFile,
    downloadFile,
    validateMetadata,
    submitEntry,
    deleteFile,
    checkUpdateToken,
}