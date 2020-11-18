import config from "../config";
import {requestGet, requestPost} from "./common";

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

export {
    getEntries,
    createEntry
}