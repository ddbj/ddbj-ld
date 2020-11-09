import {useCallback, useMemo, useState} from "react"
import {useDispatch, useSelector} from 'react-redux'
import {deleteFile, download, upload} from "../actions/file";
import {
    getAnalyzedResultFileList,
    getRawDataFileList,
    getSupplementaryFileList
} from "../services/projectBook/get/resource";
import config from "../config";
import {useIntl} from "react-intl";

const useFile = (projectId, fileType) => {
    const {
        uploadFile,
        setUploadFile,
        isLoading,
        setIsLoading,
        isDownloadLoading,
        setIsDownloadLoading,
        isDeleteLoading,
        setIsDeleteLoading,
        currentTargetName,
        setCurrentTargetName
    } = useFileState()

    const dispatch = useDispatch()
    const uploadedFiles = useUploadedFiles(projectId, fileType)

    const intl = useIntl()

    const locale = useSelector(({i18n}) => {
        return i18n.locale
    })

    const title = useMemo(() => {
        if (fileType === "analysis") {
            return intl.formatMessage({id: 'project.detail.editing.file.upload.title.analysed'})
        }

        if (fileType === "raw") {
            return intl.formatMessage({id: 'project.detail.editing.file.upload.title.raw'})
        }

        if (fileType === "project") {
            return intl.formatMessage({id: 'project.detail.editing.file.upload.title.suppl'})
        }
    }, [fileType, locale])

    const handleUpload = useCallback(() => {
        setIsLoading(true)

        dispatch(upload(projectId, fileType, uploadFile.name, uploadFile, setIsLoading))
    }, [uploadFile])

    const handleCopy = useCallback((url) => {
        navigator.clipboard.writeText(url)
    }, [])

    const handleUrlsCopy = useCallback((files) => {
        let urls = "";

        files.map((file) => {
            urls = file.url ? urls + file.url + "\n" : urls
        })

        navigator.clipboard.writeText(urls)
    }, [])

    const handleDownLoad = useCallback((name, url, external) => {
        if(external) {
            window.location.href = url
        } else {
            setIsDownloadLoading(true)
            setCurrentTargetName(name)
            dispatch(download(projectId, fileType, name, setIsDownloadLoading))
        }
    }, [])

    const handleDelete = useCallback((name) => {
        setIsDeleteLoading(true)
        setCurrentTargetName(name)
        dispatch(deleteFile(projectId, fileType, name, setIsDeleteLoading))
    }, [])

    return {
        title,
        setUploadFile,
        uploadFile,
        setIsLoading,
        isLoading,
        uploadedFiles,
        handleUpload,
        handleCopy,
        handleUrlsCopy,
        handleDownLoad,
        handleDelete,
        isDownloadLoading,
        isDeleteLoading,
        currentTargetName,
        setCurrentTargetName
    }
}

const useUploadedFiles = (projectId, fileType) => {
    const project = useSelector(({auth}) => {
        return auth.currentUser.project
    })

    return useMemo(() => {
        const target = project.find((prj) => prj.ids.id === projectId)

        const draftFiles = target.draftFileList.filter((file) => file.type === fileType)

        const metaData =
            target.draftMetadata && target.draftMetadata.metadata
                ? target.draftMetadata.metadata
                : target.metadata && target.metadata.sheets
                ? target.metadata.sheets
                : []

        let metadataFiles = null

        if("project" === fileType) {
            metadataFiles = getSupplementaryFileList(metaData)
        }

        if("raw" === fileType) {
            metadataFiles = getRawDataFileList(metaData)
        }

        if("analysis" === fileType) {
            metadataFiles = getAnalyzedResultFileList(metaData)
        }

        let notDescribedFiles = []

        draftFiles.map(file => {
            let described = false

            for (let f of metadataFiles) {
                if (f.download_url && file.url === f.download_url[0]) {
                    described = true
                }
            }

            if(false === described) {
                notDescribedFiles.push(file)
            }
        })

        let files = []

        notDescribedFiles.map(file => {
            files.push({
                name: file.name,
                url: file.url,
                external: false,
                described: false
            })
        })

        const innerUrl = config.baseURLApi

        metadataFiles.map(file => {
            let url = file.download_url ? file.download_url[0]: null
            let external = url ? url.indexOf(innerUrl) === -1 : true

            files.push({
                name: file.file_name ? file.file_name[0] : null,
                url,
                external,
                described: true
            })
        })

        return files
    }, [project])
}

const useFileState = () => {
    const [uploadFile, setUploadFile] = useState(null)
    const [isLoading, setIsLoading] = useState(false)
    const [isDownloadLoading, setIsDownloadLoading] = useState(false)
    const [isDeleteLoading, setIsDeleteLoading] = useState(false)
    const [currentTargetName, setCurrentTargetName] = useState(null)

    return {
        uploadFile,
        setUploadFile,
        isLoading,
        setIsLoading,
        isDownloadLoading,
        setIsDownloadLoading,
        isDeleteLoading,
        setIsDeleteLoading,
        currentTargetName,
        setCurrentTargetName
    }
}

export {
    useFile,
    useUploadedFiles
}