import config from "../config";
import { requestDelete, requestGet, requestPost } from "./common";

const getEntries = (accessToken) => {
    const url = config.getEntriesApi
    return requestGet(accessToken, url)
}

const createEntry = (accessToken, title, description) => {
    const url = config.createEntryApi
    const params = {
        title,
        description
    }

    return requestPost(accessToken, url, params)
}

const deleteEntry = (accessToken, entryUUID) => {
    const url = config.deleteEntryApi.replace("{entry_uuid}", entryUUID)

    return requestDelete(accessToken, url)
}

const getEntryInformation = (accessToken, entryUUID) => {
    const url = config.getEntryInformationApi.replace("{entry_uuid}", entryUUID)

    return requestGet(accessToken, url)
}

const postComment = (accessToken, entryUUID, comment) => {
    const url = config.postCommentApi.replace("{entry_uuid}", entryUUID)
    const params = {
        comment
    }

    return requestPost(accessToken, url, params)
}

const editComment = (accessToken, entryUUID, commentUUID, comment) => {
    const url = config.editCommentApi.replace("{entry_uuid}", entryUUID).replace("{comment_uuid}", commentUUID)
    const params = {
        comment
    }

    return requestPost(accessToken, url, params)
}

const deleteComment = (accessToken, entryUUID, commentUUID) => {
    const url = config.editCommentApi.replace("{entry_uuid}", entryUUID).replace("{comment_uuid}", commentUUID)

    return requestDelete(accessToken, url)
}

export {
    getEntries,
    createEntry,
    deleteEntry,
    getEntryInformation,
    postComment,
    editComment,
    deleteComment,
}