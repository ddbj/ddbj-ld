import config from "../config";
import {requestDelete, requestGet, requestPost} from "./common";

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

const deleteEntry = (accessToken, uuid) => {
    const url = config.deleteEntryApi.replace("{entry_uuid}", uuid)

    return requestDelete(accessToken, url)
}

const getEntryInformation = (accessToken, uuid) => {
    const url = config.getEntryInformationApi.replace("{entry_uuid}", uuid)

    return requestGet(accessToken, url)
}

const postComment = (accessToken, uuid, comment) => {
    const url = config.postCommentApi.replace("{entry_uuid}", uuid)
    const params = {
        comment
    }

    return requestPost(accessToken, url, params)
}

export {
    getEntries,
    createEntry,
    deleteEntry,
    getEntryInformation,
    postComment
}