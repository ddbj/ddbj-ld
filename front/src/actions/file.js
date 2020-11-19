const GET_PROJECT_FILE = 'FILE/GET_PROJECT_FILE'
const UPLOAD = 'FILE/UPLOAD'
const DOWNLOAD = 'FILE/DOWNLOAD'
const DELETE = 'FILE/DELETE'

export {
    GET_PROJECT_FILE,
    UPLOAD,
    DOWNLOAD,
    DELETE
}

const getProjectFile = (projectId) => ({
    type: GET_PROJECT_FILE,
    payload: {projectId}
})

const upload = (projectId, type, name, file, setIsLoading) => ({
    type: UPLOAD,
    payload: {projectId, type, name, file, setIsLoading}
})

const download = (projectId, type, name, setIsDownloadLoading) => ({
    type: DOWNLOAD,
    payload: {projectId, type, name, setIsDownloadLoading}
})

const deleteFile = (projectId, type, name, setIsDeleteLoading) => ({
    type: DELETE,
    payload: {projectId, type, name, setIsDeleteLoading}
})

export {
    getProjectFile,
    upload,
    download,
    deleteFile
}