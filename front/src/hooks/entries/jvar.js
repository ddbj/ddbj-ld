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
    usePagination,
    useTable,
    useFilters
} from "react-table"
import { Button } from "react-bootstrap"
import {
    getEntries,
    getEntryInformation,
    editComment,
    deleteComment,
    updateFile,
    downloadFile,
    validateMetadata,
    submitEntry,
    deleteFile,
    postComment,
} from "../../actions/entry"
import { useDropzone } from "react-dropzone"
import DefaultColumnFilter from "../../components/Filter/DefaultColumnFilter/DefaultColumnFilter"
import SelectColumnFilter from "../../components/Filter/SelectColumnFilter/SelectColumnFilter"

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

const useEditingInfo = (history, entryUUID) => {
    const [loading, setLoading] = useState(true)
    const dispatch = useDispatch()

    useEffect(() => {
        if(entryUUID) {
            dispatch(getEntryInformation(history, entryUUID, setLoading))
        }
    }, [history])

    const currentEntry = useSelector((state) => state.entry.currentEntry, [loading])

    const currentUser = useSelector((state) => state.auth.currentUser, [])

    const isEditable = useCallback((author) => (currentUser.curator || currentUser.uid == author), [])

    return {
        loading,
        currentEntry,
        isEditable,
        updateToken: currentEntry ? currentEntry.update_token : null
    }
}

const useComment = (history, entryUUID, commentUUID = null) => {
    const [comment, setComment]   = useState(null)
    // コメントがDDBJ査定者のみが見れるかどうか
    const [admin, setAdmin]       = useState(false)
    const [isLoading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/comments`), [history])

    const { currentEntry, updateToken } = useEditingInfo(history, entryUUID)

    const currentComment = useMemo(() => {
        const target = currentEntry.comments.find((comment) => comment.uuid === commentUUID)

        if (target) {
            return target
        } else {
            return { comment: null, admin: false }
        }
    }, [])

    useEffect(() => {
        const { comment, admin } = currentComment;

        setComment(comment)
        setAdmin(admin)
    }, [])

    const postIsSubmittable = useMemo(() => {
        return !!comment
    }, [comment])

    const postHandler = useCallback(event => {
        event.preventDefault()
        if (!postIsSubmittable) return

        setLoading(true)
        dispatch(postComment(history, entryUUID, updateToken, comment, admin, setLoading))
    }, [postIsSubmittable, close, comment, admin])

    const editIsSubmittable = useMemo(() => {
        return comment && comment !== currentComment
    }, [comment])

    const editHandler = useCallback(event => {
        event.preventDefault()
        if (!editIsSubmittable) return

        setLoading(true)
        dispatch(editComment(history, entryUUID, updateToken, commentUUID, comment, admin, setLoading))
    }, [editIsSubmittable, close, comment, admin])

    const deleteHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(deleteComment(history, entryUUID, updateToken, commentUUID, setLoading))
    }, [close, comment])

    // ユーザーがadminか否か
    const isCurator = useSelector((state) => state.auth.currentUser ? state.auth.currentUser.curator : false, [])

    return {
        comment,
        setComment,
        admin,
        setAdmin,
        isLoading,
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
        // FIXME 圧縮形式のバリエーションが明らかになれば詳細化する
        const vcfRegExp      = /.*\.vcf*/

        for(let file of files) {
            const isWorkBook = !!file.name.match(new RegExp(workBookRegExp))
            const isVCF      = !!file.name.match(new RegExp(vcfRegExp))

            if(false == (isWorkBook || isVCF)) {
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
            setErrorDescription("The supported file formats are Excel (.xlsx) or Variant Call Format (.vcf).")
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

export {
    useEntries,
    useEditingInfo,
    useComment,
    useFiles,
    useValidate,
    useSubmit,
    useDeleteFile,
}