import React, {useCallback, useEffect, useMemo, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useIntl} from "react-intl";
import {usePagination, useTable, useSortBy} from "react-table";
import {Button} from "react-bootstrap";
import {
    getEntries,
    getEntryInformation,
    editComment,
    deleteComment,
    updateFile,
    downloadFile, validateMetadata
} from "../../actions/entry";
import {useDropzone} from "react-dropzone";

const useEntries = (history) => {
    const dispatch = useDispatch()
    const intl = useIntl()

    useEffect(() => {
        dispatch(getEntries(history))
    }, [])

    const entries = useSelector((state) => state.entry.entries, [])

    const columns = useMemo(() => ([{
        id: 'title',
        Header: intl.formatMessage({id: 'common.header.title'}),
        accessor: 'title'
    }, {
        id: 'status',
        Header: intl.formatMessage({id: 'common.header.status'}),
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
                    <>
                        <Button onClick={() => history.push(`/entries/jvar/${cell.row.original.uuid}`)}>{intl.formatMessage({id: 'common.button.edit'})}</Button>
                        {'　'}
                        {cell.row.original.isDeletable
                            ? <Button variant={"danger"} onClick={() => history.push(`/entries/jvar/${cell.row.original.uuid}/delete`)}>{intl.formatMessage({id: 'common.button.delete'})}</Button>
                            : null
                        }
                    </>
                )
            default:
                return <span>{cell.value}</span>
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
        id: 'url',
        Header: "url",
        accessor: 'url'
    }, {
        id: 'button',
        Header: '',
        accessor: 'button'
    }]), [])

    const fileRenderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                return (
                    <>
                        <Button
                            variant={"primary"}
                            onClick={() => dispatch(downloadFile(history, entryUUID, cell.row.original.type, cell.row.original.name))}
                        >
                            Download
                        </Button>
                    </>
                )
            default:
                return <span>{cell.value}</span>
        }
    }, [])

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
    const { loading } = useEditingInfo(history, entryUUID)

    const validateFiles = useCallback(files => {
        const workBookRegExp = /.*.xlsx$/
        const vcfRegExp      = /.*.vcf$/

        for(let file of files) {
            const isWorkBook = !!file.name.match(new RegExp(workBookRegExp))
            const isVCF      = !!file.name.match(new RegExp(vcfRegExp))

            if(false == (isWorkBook || isVCF)) {
                return false
            }
        }

        return true
    }, [])

    const dispatch = useDispatch()

    const onDrop = useCallback(files => {
        if(validateFiles(files)) {
            history.push(`/entries/jvar/${entryUUID}/files/upload/loading`)

            for(let file of files) {
                const type = !!file.name.match(new RegExp(/.*.xlsx$/)) ? "workbook" : "vcf"
                const name = file.name

                dispatch(updateFile(history, entryUUID, type, name, file))
            }

            history.push(`/entries/jvar/${entryUUID}/files/upload`)
        } else {
            history.push(`/entries/jvar/${entryUUID}/files/upload/error`)
        }
    }, [])

    const { getRootProps, getInputProps } = useDropzone({onDrop})

    return {
        getRootProps,
        getInputProps,
        loading,
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

export {
    useEntries,
    useEditingInfo,
    useComment,
    useFiles,
    useValidate,
}