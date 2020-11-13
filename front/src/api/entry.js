import config from "../config";
import {requestGet} from "./common";

const getEntries = (accessToken) => {
    const url = config.getEntriesApi
    return requestGet(accessToken, url)
}

const createEntry = (accessToken) => {

}

export {
    getEntries,
    createEntry
}