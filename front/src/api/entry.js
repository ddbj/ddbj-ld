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

export {
    getEntries,
    createEntry,
    deleteEntry,
    getEntryInformation
}