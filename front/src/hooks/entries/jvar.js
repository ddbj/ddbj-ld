import React, {useCallback, useEffect, useMemo, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useIntl} from "react-intl";
import {usePagination, useTable, useSortBy} from "react-table";
import {Button} from "react-bootstrap";
import {getEntries, getEntryInformation} from "../../actions/entry";

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

const useEditingInfo = (history, uuid) => {
    const [loading, setLoading] = useState(false)
    const dispatch = useDispatch()

    useEffect(() => {
        if(uuid) {
            setLoading(true)
            dispatch(getEntryInformation(history, uuid, setLoading))
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
                        <Button variant={"primary"}>Download</Button>
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

    const commentRenderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                return (
                    <>
                        <Button variant={"primary"}>Update</Button>
                        {'　'}
                        <Button variant={"danger"}>Delete</Button>
                    </>
                )
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
        commentInstance
    }
}

export {
    useEntries,
    useEditingInfo
}