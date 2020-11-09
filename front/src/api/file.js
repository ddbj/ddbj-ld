import {requestDelete, requestGet, requestPost} from "./common"
import config from "../config"

// ファイル（NextCloud）に対するAPI

const getProjectFile = (projectId) => {

}

const preUpload = (accessToken, projectId, type, name) => {
    const url = config.preUploadApi
        .replace("{project-id}", projectId)
        .replace("{file-type}", type)
        .replace("{file-name}", name)

    return requestPost(accessToken, url, null)
}

const upload = async (projectId, type, name, body, token) => {
    const method = "POST"

    const url = config.uploadApi
        .replace("{project-id}", projectId)
        .replace("{file-type}", type)
        .replace("{file-name}", name)
        .replace("{token}", token)

    const mode = 'cors'

    return await fetch(url
        , {method, body, mode})
        .then(response => {
            return response
        })
        .catch(error => ({error}))
}

const download = (accessToken, projectId, fileType, fileName) => {
    const url = config.downloadApi
        .replace("{project-id}", projectId)
        .replace("{file-type}", fileType)
        .replace("{file-name}", fileName)

    return requestGet(accessToken, url)
}

// 予約語のため、deleteとしなかった
const deleteFile = (accessToken, projectId, fileType, fileName) => {
    const url = config.deleteApi
        .replace("{project-id}", projectId)
        .replace("{file-type}", fileType)
        .replace("{file-name}", fileName)

    return requestDelete(accessToken, url)
}

export {
    getProjectFile,
    preUpload,
    upload,
    download,
    deleteFile
}