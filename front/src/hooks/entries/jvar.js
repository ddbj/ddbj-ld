import React, {useCallback, useEffect, useMemo, useState} from "react"
import {useDispatch, useSelector} from "react-redux"
import {useIntl} from "react-intl"
import {usePagination, useTable, useSortBy} from "react-table"
import {Button} from "react-bootstrap"
import {
    getEntries,
    getEntryInformation,
    editComment,
    deleteComment,
    updateFile,
    downloadFile,
    validateMetadata,
    submitEntry,
} from "../../actions/entry"
import {useDropzone} from "react-dropzone"

const useEntries = (history) => {
    const dispatch = useDispatch()
    const intl = useIntl()

    useEffect(() => {
        dispatch(getEntries(history))
    }, [])

    const entries = useSelector((state) => state.entry.entries, [])

    // FIXME 統計情報を追加
    const columns = useMemo(() => ([{
        id: 'uuid',
        Header: 'uuid',
        accessor: 'uuid'
    }, {
        id: 'label',
        Header: 'label',
        accessor: 'label'
    }, {
        id: 'type',
        Header: 'type',
        accessor: 'type'
    }, {
        id: 'status',
        Header: 'status',
        accessor: 'status'
    }, {
        id: 'button',
        Header: '',
        accessor: 'button'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                return (
                    <div style={{ width: 200 }}>
                        <Button onClick={() => history.push(`/entries/jvar/${cell.row.original.uuid}`)}>Edit</Button>
                        {'　'}
                        {cell.row.original.isDeletable
                            ? <Button variant={"danger"} onClick={() => history.push(`/entries/jvar/${cell.row.original.uuid}/delete`)}>Delete</Button>
                            : null
                        }
                    </div>
                )
            default:
                return <div style={{ width: 300 }}>{cell.value}</div>
        }
    }, [intl])

    const instance = useTable({
        columns,
        data: entries ? entries : [],
    }, useSortBy, usePagination)

    return {
        renderCell,
        instance,
    }
}

const useEditingInfo = (history, entryUUID) => {
    const [loading, setLoading] = useState(false)
    const dispatch = useDispatch()

    useEffect(() => {
        if(entryUUID) {
            setLoading(true)
            dispatch(getEntryInformation(history, entryUUID, setLoading))
        }
    }, [history])

    const currentEntry = useSelector((state) => state.entry.currentEntry, [loading])

    const fileColumns = useMemo(() => ([{
        id: 'name',
        Header: "name",
        accessor: 'name'
    }, {
        id: 'type',
        Header: "type",
        accessor: 'type'
    }, {
        id: 'validation_status',
        Header: "validation status",
        accessor: 'validation_status'
    }, {
        id: 'button',
        Header: '',
        accessor: 'button'
    }]), [])

    const fileRenderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                return (
                    <div style={{width: 275}}>
                        <Button
                            variant={"primary"}
                            size={"sm"}
                            onClick={() => dispatch(downloadFile(history, entryUUID, cell.row.original.type, cell.row.original.name))}
                        >
                            Download
                        </Button>
                        {'　'}
                        <Button
                            variant={"info"}
                            onClick={null}
                        >
                            History
                        </Button>
                        {'　'}
                        <Button
                            variant={"danger"}
                            onClick={null}
                        >
                            Delete
                        </Button>
                    </div>
                )
            case 'name':
                return <span>{cell.value}</span>
            default:
                return <div style={{width :70}}>{cell.value}</div>
        }
    }, [history])

    const fileInstance = useTable({
        columns: fileColumns,
        data: currentEntry ? currentEntry.files : [],
        initialState: {},
    }, usePagination)

    const commentColumns = useMemo(() => ([{
        id: 'comment',
        Header: "comment",
        accessor: 'comment'
    }, {
        id: 'author',
        Header: "author",
        accessor: 'author'
    }, {
        id: 'button',
        Header: "",
        accessor: 'button'
    }]), [currentEntry])

    const currentUser = useSelector((state) => state.auth.currentUser, [])

    const isEditable = useCallback((author) => (currentUser.admin || currentUser.uid == author), [])

    const commentRenderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                if(isEditable(cell.row.original.author)) {
                    return (
                        <>
                            <Button
                                variant={"primary"}
                                onClick={() => history.push(`/entries/jvar/${entryUUID}/comments/${cell.row.original.uuid}/edit`)}
                            >
                                Edit
                            </Button>
                            {'　'}
                            <Button
                                variant={"danger"}
                                onClick={() => history.push(`/entries/jvar/${entryUUID}/comments/${cell.row.original.uuid}/delete`)}
                            >
                                Delete
                            </Button>
                        </>
                    )
                } else {
                    return null
                }
            default:
                return <span>{cell.value}</span>
        }
    }, [currentEntry])

    const commentInstance = useTable({
        columns: commentColumns,
        data: currentEntry ? currentEntry.comments : [],
        initialState: {},
    }, usePagination)

    return {
        loading,
        currentEntry,
        fileRenderCell,
        fileInstance,
        commentRenderCell,
        commentInstance,
    }
}

const useComment = (history, entryUUID, commentUUID) => {
    const [comment, setComment] = useState(null)
    const [isLoading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/comments`), [history])

    const { currentEntry } = useEditingInfo(history, entryUUID)

    const currentComment = useMemo(() => {
        const target = currentEntry.comments.find((comment) => comment.uuid === commentUUID)

        if (target) {
            return target.comment
        } else {
            return null
        }
    }, [])

    useEffect(() => {
        setComment(currentComment)
    }, [])

    const editIsSubmittable = useMemo(() => {
        return comment && comment !== currentComment
    }, [comment])

    const editHandler = useCallback(event => {
        event.preventDefault()
        if (!editIsSubmittable) return

        setLoading(true)
        dispatch(editComment(history, entryUUID, commentUUID, comment, setLoading))
    }, [editIsSubmittable, close, comment])

    const deleteHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(deleteComment(history, entryUUID, commentUUID, setLoading))
    }, [close, comment])

    return {
        comment,
        setComment,
        isLoading,
        setLoading,
        close,
        editIsSubmittable,
        editHandler,
        deleteHandler,
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

    const { loading, currentEntry } = useEditingInfo(history, entryUUID)

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
    }, [entryUUID])

    const validateDuplicateWorkBook = useCallback(files => {
        const workBookRegExp = /.*\.xlsx$/

        const workBook = files.find((file) => file.name.match(new RegExp(workBookRegExp)))

        if(null == workBook) {
            return true
        }

        return undefined === currentEntry.files.find((f) => f.type === "workbook" && f.name !== workBook.name)
    }, [entryUUID])

    const dispatch = useDispatch()

    const uploadFiles = useCallback(files => {
        setUploading(true)
        history.push(`/entries/jvar/${entryUUID}/files/loading`)

        for(let file of files) {
            const type = !!file.name.match(new RegExp(/.*.xlsx$/)) ? "workbook" : "vcf"
            const name = file.name

            dispatch(updateFile(history, entryUUID, type, name, file))
        }

        history.push(`/entries/jvar/${entryUUID}/files`)
    }, [entryUUID])

    const onUpload = useCallback(files => {
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
    }, [entryUUID])

    const { getRootProps, getInputProps } = useDropzone({ onDrop: onUpload })

    const onSelect = useCallback(() => {
        onUpload(document.getElementById("files").files)
    }, [entryUUID])

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
    }
}

const useValidate = (history, entryUUID) => {
    const [isLoading, setLoading] = useState(false)

    const dispatch = useDispatch()

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}`), [history])

    const { currentEntry } = useEditingInfo(history, entryUUID)

    const validateIsSubmittable = useMemo(() => {
        return currentEntry.menu.validate
    }, [])

    const validateHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(validateMetadata(history, entryUUID, setLoading))
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

    const { currentEntry } = useEditingInfo(history, entryUUID)

    const isSubmittable = useMemo(() => {
        return currentEntry.validation_status === 'Valid' && currentEntry.status === 'Unsubmitted'
    }, [])

    const submitHandler = useCallback(event => {
        event.preventDefault()

        setLoading(true)
        dispatch(submitEntry(history, entryUUID, setLoading))
    }, [])

    return {
        isLoading,
        setLoading,
        close,
        isSubmittable,
        submitHandler,
    }
}


export {
    useEntries,
    useEditingInfo,
    useComment,
    useFiles,
    useValidate,
    useSubmit,
}