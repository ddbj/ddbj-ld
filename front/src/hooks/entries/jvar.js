import React, {
    useCallback,
    useEffect,
    useMemo,
    useState
} from "react"
import {
    useDispatch,
    useSelector
} from "react-redux"
import {
    getEntries,
    getEntryInfo,
    editComment,
    deleteComment,
    updateFile,
    downloadFile,
    validateMetadata,
    submitEntry,
    deleteFile,
    postComment,
    createRequest,
    editRequest,
    cancelRequest,
    applyRequest,
    deleteEntry,
    updateStatus,
} from "../../actions/entry"
import { useDropzone } from "react-dropzone"

const useEntries = (history) => {
    const dispatch = useDispatch()
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        dispatch(getEntries(history, setLoading))
    }, [])

    return {
        loading,
    }
}

const useDeleteEntry = (history, entryUUID) => {
    const dispatch = useDispatch()
    const [loading, setLoading] = useState(false)

    const close = useCallback(() => history.push(`/entries/jvar`), [history])

    const entry = useSelector((state) => state.entry.entries ? state.entry.entries.find(entry => entry.uuid === entryUUID) : null, [entryUUID])

    const updateToken = useMemo(() => {
        return entry ? entry.update_token : null
    }, [entryUUID])

    const deleteHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(deleteEntry(history, entryUUID, updateToken, setLoading))
    }, [close, entryUUID])

    return {
        close,
        loading,
        setLoading,
        deleteHandler,
        updateToken,
    }
}


const useEditingInfo = (history, entryUUID) => {
    const [loading, setLoading] = useState(true)
    const dispatch = useDispatch()

    useEffect(() => {
        if(entryUUID) {
            dispatch(getEntryInfo(history, entryUUID, setLoading))
        }
    }, [history])

    const currentEntry = useSelector((state) => state.entry.currentEntry, [history])

    const currentUser = useSelector((state) => state.auth.currentUser, [])

    const isEditable = useCallback((author) => (currentUser.curator || currentUser.uid == author), [])

    return {
        loading,
        currentEntry,
        uuid: currentEntry ? currentEntry.uuid : null,
        label: currentEntry ? currentEntry.label : null,
        type: currentEntry ? currentEntry.type : null,
        status: currentEntry ? currentEntry.status : null,
        validationStatus: currentEntry ? currentEntry.validation_status : null,
        validate: currentEntry ? currentEntry.menu.validate : false,
        submit: currentEntry ? currentEntry.menu.submit : false,
        toUnsubmitted: currentEntry && currentEntry.curator_menu ? currentEntry.curator_menu.to_unsubmitted : false,
        toPrivate: currentEntry && currentEntry.curator_menu? currentEntry.curator_menu.to_private : false,
        toPublic: currentEntry && currentEntry.curator_menu? currentEntry.curator_menu.to_public : false,
        toSuppressed: currentEntry && currentEntry.curator_menu? currentEntry.curator_menu.to_suppressed : false,
        toKilled: currentEntry && currentEntry.curator_menu? currentEntry.curator_menu.to_killed : false,
        toReplaced: currentEntry && currentEntry.curator_menu? currentEntry.curator_menu.to_replaced : false,
        isEditable,
        updateToken: currentEntry ? currentEntry.update_token : null,
    }
}

const useComment = (history, entryUUID, commentUUID = null) => {
    const [comment, setComment]   = useState(null)
    // コメントがDDBJ査定者のみが見れるかどうか
    const [curator, setCurator]   = useState(false)
    const [loading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/comments`), [history])

    const { currentEntry, updateToken } = useEditingInfo(history, entryUUID)

    const currentComment = useMemo(() => {
        const target = currentEntry.comments.find((comment) => comment.uuid === commentUUID)

        if (target) {
            const { comment, curator } = target
            return { comment, curator }
        } else {
            return { comment: null, curator: false }
        }
    }, [])

    useEffect(() => {
        const { comment, curator } = currentComment;

        setComment(comment)
        setCurator(curator)
    }, [])

    const postIsSubmittable = useMemo(() => {
        return !!comment
    }, [comment])

    const postHandler = useCallback(event => {
        event.preventDefault()
        if (!postIsSubmittable) return

        setLoading(true)
        dispatch(postComment(history, entryUUID, updateToken, comment, curator, setLoading))
    }, [postIsSubmittable, close, comment, curator])

    const editIsSubmittable = useMemo(() => {
        return comment && comment !== currentComment
    }, [comment])

    const editHandler = useCallback(event => {
        event.preventDefault()
        if (!editIsSubmittable) return

        setLoading(true)
        dispatch(editComment(history, entryUUID, updateToken, commentUUID, comment, curator, setLoading))
    }, [editIsSubmittable, close, comment, curator])

    const deleteHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(deleteComment(history, entryUUID, updateToken, commentUUID, setLoading))
    }, [close, comment])

    // ユーザーがcuratorか否か
    const isCurator = useSelector((state) => state.auth.currentUser ? state.auth.currentUser.curator : false, [])

    return {
        comment,
        setComment,
        curator,
        setCurator,
        loading,
        setLoading,
        close,
        postIsSubmittable,
        postHandler,
        editIsSubmittable,
        editHandler,
        deleteHandler,
        isCurator,
    }
}

const useFiles = (history, entryUUID) => {
    const [hasError, setHasError]                 = useState(false)
    const [uploading, setUploading]               = useState(false)
    const [overwriting, setOverwriting]           = useState(false)
    const [errorTitle, setErrorTitle]             = useState(null)
    const [errorDescription, setErrorDescription] = useState(null)
    const [overwriteDescription, setOverwriteDescription]  = useState(null)
    const [currentFiles, setCurrentFiles]  = useState(null)

    const { loading, currentEntry, updateToken } = useEditingInfo(history, entryUUID)

    const validateFiles = useCallback(files => {
        const workBookRegExp = /.*\.xlsx$/
        const vcfRegExp      = /.*\.vcf*/
        const vcfGzRegExp    = /.*\.vcf.gz*/


        for(let file of files) {
            const isWorkBook = !!file.name.match(new RegExp(workBookRegExp))
            const isVcf      = !!file.name.match(new RegExp(vcfRegExp))
            const isVcfGz    = !!file.name.match(new RegExp(vcfGzRegExp))

            const isNotSupported = false === (isWorkBook || isVcf || isVcfGz)

            if(isNotSupported) {
                return false
            }
        }

        return true
    }, [currentEntry])

    const validateDuplicateWorkBook = useCallback(files => {
        const workBookRegExp = /.*\.xlsx$/

        const workBook = files.find((file) => file.name.match(new RegExp(workBookRegExp)))

        if(null == workBook) {
            return true
        }

        return undefined === currentEntry.files.find((f) => f.type === "WORKBOOK" && f.name !== workBook.name)
    }, [currentEntry])

    const dispatch = useDispatch()

    const uploadFiles = useCallback(files => {
        setUploading(true)
        history.push(`/entries/jvar/${entryUUID}/files/loading`)

        for(let file of files) {
            const type = !!file.name.match(new RegExp(/.*.xlsx$/)) ? "WORKBOOK" : "VCF"
            const name = file.name

            dispatch(updateFile(history, entryUUID, updateToken, type, name, file))
        }

        history.push(`/entries/jvar/${entryUUID}/files`)
    }, [entryUUID])

    const onDrop = useCallback(files => {
        if(validateFiles(files)) {
            // 何もしない
        } else {
            setHasError(true)
            setErrorTitle("Upload error!")
            setErrorDescription("The supported file formats are Excel (.xlsx) or Variant Call Format (.vcf or .vcf.gz).")
            history.push(`/entries/jvar/${entryUUID}/files/error`)

            return
        }

        if(validateDuplicateWorkBook(files)) {
            // 何もしない
        } else {
            setHasError(true)
            setErrorTitle("This entry has other workbook!")
            setErrorDescription("Entry can have only one excel file.")
            history.push(`/entries/jvar/${entryUUID}/files/error`)

            return
        }

        let overwriteFiles = []

        for(let file of files) {
            // FIXME currentEntryが更新されず削除→すぐに同じファイルをアップロードとすると上書き表示してしまう
            //   - Reduxの修正方法が分かり次第修正
            if(currentEntry.files.find((f) => f.name === file.name)) {
                overwriteFiles.push(file.name)
            }
        }

        if(overwriteFiles.length > 0) {
            setOverwriting(true)
            setOverwriteDescription(overwriteFiles.join())
            setCurrentFiles(files)
            history.push(`/entries/jvar/${entryUUID}/files/apply`)

            return
        }

        uploadFiles(files)
    }, [currentEntry])

    const { getRootProps, getInputProps } = useDropzone({ onDrop })

    const onSelect = useCallback(() => {
        const files = Array.from(document.getElementById("files").files)
        onDrop(files)
    }, [currentEntry])

    const downloadHandler = useCallback((type, name) => {
        dispatch(downloadFile(history, entryUUID, type, name))
    }, [currentEntry])

    return {
        getRootProps,
        getInputProps,
        loading,
        onSelect,
        hasError,
        uploading,
        errorTitle,
        errorDescription,
        overwriting,
        overwriteDescription,
        uploadFiles,
        currentFiles,
        downloadHandler,
    }
}

// FIXME 重複している
// const useRequests = (history, entryUUID, requestUUID = null) => {
//     const [type, setType]         = useState(null)
//     const [comment, setComment]   = useState(null)
//     const [isLoading, setLoading] = useState(false)
//
//     const dispatch = useDispatch()
//
//     const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/requests`), [history])
//
//     const { currentEntry, updateToken } = useEditingInfo(history, entryUUID)
//
//     const currentRequest = useMemo(() => {
//         const target = currentEntry.requests.find((request) => request.uuid === requestUUID)
//
//         if (target) {
//             const { type, comment } = target
//             return { type, comment }
//         } else {
//             return { type: null, comment: null }
//         }
//
//         if(validateDuplicateWorkBook(files)) {
//             // 何もしない
//         } else {
//             setHasError(true)
//             setErrorTitle("This entry has other workbook!")
//             setErrorDescription("Entry can have only one excel file.")
//             history.push(`/entries/jvar/${entryUUID}/files/error`)
//
//             return
//         }
//
//         let overwriteFiles = []
//
//         for(let file of files) {
//             // FIXME currentEntryが更新されず削除→すぐに同じファイルをアップロードとすると上書き表示してしまう
//             //   - Reduxの修正方法が分かり次第修正
//             if(currentEntry.files.find((f) => f.name === file.name)) {
//                 overwriteFiles.push(file.name)
//             }
//         }
//
//         if(overwriteFiles.length > 0) {
//             setOverwriting(true)
//             setOverwriteDescription(overwriteFiles.join())
//             setCurrentFiles(files)
//             history.push(`/entries/jvar/${entryUUID}/files/apply`)
//
//             return
//         }
//
//         uploadFiles(files)
//     }, [currentEntry])
//
//     const { getRootProps, getInputProps } = useDropzone({ onDrop })
//
//     const onSelect = useCallback(() => {
//         const files = Array.from(document.getElementById("files").files)
//         onDrop(files)
//     }, [currentEntry])
//
//     const downloadHandler = useCallback((type, name) => {
//         dispatch(downloadFile(history, entryUUID, type, name))
//     }, [currentEntry])
//
//     return {
//         getRootProps,
//         getInputProps,
//         loading,
//         onSelect,
//         hasError,
//         uploading,
//         errorTitle,
//         errorDescription,
//         overwriting,
//         overwriteDescription,
//         uploadFiles,
//         currentFiles,
//         downloadHandler,
//     }
// }

const useRequests = (history, entryUUID, requestUUID = null) => {
    const [type, setType]         = useState(null)
    const [comment, setComment]   = useState(null)
    const [isLoading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/requests`), [history])

    const { currentEntry, updateToken } = useEditingInfo(history, entryUUID)

    const currentRequest = useMemo(() => {
        const target = currentEntry.requests.find((request) => request.uuid === requestUUID)

        if (target) {
            const { type, comment } = target
            return { type, comment }
        } else {
            return { type: null, comment: null }
        }
    }, [])

    useEffect(() => {
        const { type, comment } = currentRequest;

        setType(type)
        setComment(comment)
    }, [])

    const requestIsSubmittable = useMemo(() => {
        return !!type
    }, [type])

    const requestHandler = useCallback(event => {
        event.preventDefault()
        if (!requestIsSubmittable) return

        setLoading(true)
        dispatch(createRequest(history, entryUUID, updateToken, type, comment, setLoading))
    }, [requestIsSubmittable, close, type, comment])

    const editIsSubmittable = useMemo(() => {
        return comment !== currentRequest.comment
    }, [comment])

    const editHandler = useCallback(event => {
        event.preventDefault()
        if (!editIsSubmittable) return

        setLoading(true)
        dispatch(editRequest(history, entryUUID, updateToken, requestUUID, comment, setLoading))
    }, [editIsSubmittable, close, comment])

    const currentUser = useSelector((state) => state.auth.currentUser, [])

    const isEditable = useCallback((author) => (currentUser.curator || currentUser.uid == author), [])

    const cancelHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(cancelRequest(history, entryUUID, updateToken, requestUUID, setLoading))
    }, [close, comment])

    const applyHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(applyRequest(history, entryUUID, updateToken, requestUUID, setLoading))
    }, [close, comment])

    return {
        type,
        setType,
        comment,
        setComment,
        isLoading,
        setLoading,
        close,
        requestIsSubmittable,
        requestHandler,
        isEditable,
        editIsSubmittable,
        editHandler,
        cancelHandler,
        applyHandler,
    }
}

const useValidate = (history, entryUUID) => {
    const [isLoading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}`), [history])

    const { currentEntry, updateToken } = useEditingInfo(history, entryUUID)

    const validateIsSubmittable = useMemo(() => {
        return currentEntry.menu.validate
    }, [])

    useEffect(() => {
        const { type, comment } = currentRequest;

        setType(type)
        setComment(comment)
    }, [])

    const requestIsSubmittable = useMemo(() => {
        return !!type
    }, [type])

    const requestHandler = useCallback(event => {
        event.preventDefault()
        if (!requestIsSubmittable) return

        setLoading(true)
        dispatch(createRequest(history, entryUUID, updateToken, type, comment, setLoading))
    }, [requestIsSubmittable, close, type, comment])

    const editIsSubmittable = useMemo(() => {
        return comment !== currentRequest.comment
    }, [comment])

    const editHandler = useCallback(event => {
        event.preventDefault()
        if (!editIsSubmittable) return

        setLoading(true)
        dispatch(editRequest(history, entryUUID, updateToken, requestUUID, comment, setLoading))
    }, [editIsSubmittable, close, comment])

    const currentUser = useSelector((state) => state.auth.currentUser, [])

    const isEditable = useCallback((author) => (currentUser.curator || currentUser.uid == author), [])

    const cancelHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(cancelRequest(history, entryUUID, updateToken, requestUUID, setLoading))
    }, [close, comment])

    const applyHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(applyRequest(history, entryUUID, updateToken, requestUUID, setLoading))
    }, [close, comment])

    return {
        type,
        setType,
        comment,
        setComment,
        isLoading,
        setLoading,
        close,
        requestIsSubmittable,
        requestHandler,
        isEditable,
        editIsSubmittable,
        editHandler,
        cancelHandler,
        applyHandler,
    }
}

const useValidate = (history, entryUUID) => {
    const [isLoading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}`), [history])

    const { currentEntry, updateToken } = useEditingInfo(history, entryUUID)

    const validateIsSubmittable = useMemo(() => {
        return currentEntry.menu.validate
    }, [])

    const validateHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(validateMetadata(history, entryUUID, updateToken, setLoading))
    }, [])

    return {
        isLoading,
        setLoading,
        close,
        validateIsSubmittable,
        validateHandler,
    }
}

const useSubmit = (history, entryUUID) => {
    const [isLoading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}`), [history])

    const { currentEntry, updateToken } = useEditingInfo(history, entryUUID)

    const isSubmittable = useMemo(() => {
        return currentEntry.validation_status === 'Valid' && currentEntry.status === 'Unsubmitted'
    }, [])

    const submitHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(submitEntry(history, entryUUID, updateToken, setLoading))
    }, [])

    return {
        isLoading,
        close,
        isSubmittable,
        submitHandler,
    }
}

const useDeleteFile = (history, entryUUID, fileType, fileName) => {
    const [isLoading, setLoading] = useState(false)
    const { updateToken } = useEditingInfo(history, entryUUID)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}`), [history])

    const deleteHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(deleteFile(history, entryUUID, updateToken, fileType, fileName, setLoading))
    }, [])

    return {
        isLoading,
        close,
        deleteHandler,
    }
}

const useStatus = (history, entryUUID, status) => {
    const [loading, setLoading] = useState(false)
    const { updateToken } = useEditingInfo(history, entryUUID)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}`), [history])

    const submitHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(updateStatus(history, entryUUID, updateToken, status, setLoading))
    }, [])

    return {
        loading,
        close,
        submitHandler,
    }
}

export {
    useEntries,
    useDeleteEntry,
    useEditingInfo,
    useComment,
    useFiles,
    useValidate,
    useSubmit,
    useDeleteFile,
    useRequests,
    useStatus,
}
