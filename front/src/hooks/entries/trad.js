import config from '../../config'
import {useCurrentUser} from "../auth";
import React, {useCallback, useEffect, useMemo, useState} from "react";
import {useDispatch} from "react-redux";
import {ListViewValue as Value} from "../../pages/project/components/Value";
import {useIntl} from "react-intl";
import {usePagination, useTable} from "react-table";
import {Button} from "react-bootstrap";

const useEntries = (history) => {
    const dispatch = useDispatch()
    const currentUser = useCurrentUser()
    const [entries, setEntries] = useState([])
    const intl = useIntl()

    const uuid = currentUser ? currentUser.uuid : null

    const getEntries = useCallback(() => {
        if(config.isDummy) {
            // ダミーデータを返却する
            setEntries([
                {
                    uuid : "a27009a7-76a0-7b85-83d7-4a972ceac023",
                    title: "Test Entry 1",
                    status: "Unsubmitted"
                },
                {
                    uuid : "5c8681ef-48f0-d3ec-93c6-620698bb5744",
                    title: "Test Entry 2",
                    status: "Submitted"
                },
                {
                    uuid : "f01e8454-e723-fe9c-39f5-a0464f94142b",
                    title: "Test Entry 3",
                    status: "Public"
                }
            ])
        }

        // TODO dispatchする処理を追加
    }, [uuid])

    useEffect(() => {
        getEntries()
    }, [])

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
                        <Button onClick={() => history.push(`/entries/trad/${cell.row.original.uuid}`)}>{intl.formatMessage({id: 'common.button.edit'})}</Button>
                        {'　'}
                        {"Unsubmitted" === cell.row.original.status ? <Button variant={"danger"}>{intl.formatMessage({id: 'common.button.delete'})}</Button>: null}
                    </>
                )
            default:
                return <span>{cell.value}</span>
        }
    }, [intl])

    const instance = useTable({
        columns,
        data: entries,
        initialState: {},
    }, usePagination)

    return {
        renderCell,
        instance
    }
}

const useEditingInfo = (uuid) => {
    const [data, setData] = useState(null)

    useEffect(() => {
        const testDataList = {
            "a27009a7-76a0-7b85-83d7-4a972ceac023": {
                uuid : "a27009a7-76a0-7b85-83d7-4a972ceac023",
                title: "Test Entry 1",
                description: "Test Entry 1 description",
                entryStatus: "Unsubmitted",
                validationStatus: "Valid",
                fileList: [{
                    name: "dummy.xlsx",
                    url: "http://dummy.ddbj.co.jp/dummy.xlsx",
                    type: "excel",
                    validationStatus: "Valid",
                    validationInfo: null
                },
                    {
                        name: "dummy.vcf",
                        url: "http://dummy.ddbj.co.jp/dummy.vcf",
                        type: "vcf",
                        validationStatus: "Valid",
                        validationInfo: null
                    }
                ],
                validationSummary: [
                    {
                        name: "project",
                        status: "Valid",
                        description: "",
                        file: "dummy.xlsx"
                    },
                    {
                        name: "assays",
                        status: "Valid",
                        description: "",
                        file: "dummy.xlsx"
                    }
                ],
                comments: [
                    {
                        comment: "test comment",
                        author: "test user 1"
                    },
                    {
                        comment: "test comment 2",
                        author: "test user 2"
                    }
                ]
            },
            "5c8681ef-48f0-d3ec-93c6-620698bb5744": {
                uuid : "5c8681ef-48f0-d3ec-93c6-620698bb5744",
                title: "Test Entry 2",
                description: "Test Entry 2 description",
                entryStatus: "Submitted",
                validationStatus: "Valid",
                fileList: [{
                    name: "dummy.xlsx",
                    url: "http://dummy.ddbj.co.jp/dummy.xlsx",
                    type: "excel",
                    validationStatus: "Valid",
                    validationInfo: null
                },
                    {
                        name: "dummy.vcf",
                        url: "http://dummy.ddbj.co.jp/dummy.vcf",
                        type: "vcf",
                        validationStatus: "Valid",
                        validationInfo: null
                    }
                ],
                validationSummary: [
                    {
                        name: "project",
                        status: "Valid",
                        description: "",
                        file: "dummy.xlsx"
                    },
                    {
                        name: "assays",
                        status: "Valid",
                        description: "",
                        file: "dummy.xlsx"
                    }
                ],
                comments: [
                    {
                        comment: "test comment",
                        author: "test user 1"
                    },
                    {
                        comment: "test comment 2",
                        author: "test user 2"
                    }
                ]
            },
            "f01e8454-e723-fe9c-39f5-a0464f94142b": {
                uuid : "f01e8454-e723-fe9c-39f5-a0464f94142b",
                title: "Test Entry 3",
                description: "Test Entry 3 description",
                entryStatus: "Public",
                validationStatus: "Valid",
                fileList: [{
                    name: "dummy.xlsx",
                    url: "http://dummy.ddbj.co.jp/dummy.xlsx",
                    type: "excel",
                    validationStatus: "Valid",
                    validationInfo: null
                },
                    {
                        name: "dummy.vcf",
                        url: "http://dummy.ddbj.co.jp/dummy.vcf",
                        type: "vcf",
                        validationStatus: "Valid",
                        validationInfo: null
                    }
                ],
                validationSummary: [
                    {
                        name: "project",
                        status: "Valid",
                        description: "",
                        file: "dummy.xlsx"
                    },
                    {
                        name: "assays",
                        status: "Valid",
                        description: "",
                        file: "dummy.xlsx"
                    }
                ],
                comments: [
                    {
                        comment: "test comment",
                        author: "test user 1"
                    },
                    {
                        comment: "test comment 2",
                        author: "test user 2"
                    }
                ]
            },
        }

        setData(testDataList[uuid])
    }, [uuid])

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
        data: data ? data.fileList : [],
        initialState: {},
    }, usePagination)

    const validationColumns = useMemo(() => ([{
        id: 'name',
        Header: "name",
        accessor: 'name'
    }, {
        id: 'status',
        Header: "status",
        accessor: 'status'
    },  {
        id: 'file',
        Header: "file",
        accessor: 'file'
    }, {
        id: 'button',
        Header: '',
        accessor: 'button'
    }]), [])

    const validationRenderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                return (
                    <>
                        <Button variant={"primary"}>Detail</Button>
                    </>
                )
            default:
                return <span>{cell.value}</span>
        }
    }, [])

    const validationInstance = useTable({
        columns: validationColumns,
        data: data ? data.validationSummary : [],
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
    }]), [])

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
    }, [])

    const commentInstance = useTable({
        columns: commentColumns,
        data: data ? data.comments : [],
        initialState: {},
    }, usePagination)

    return {
        data,
        fileRenderCell,
        fileInstance,
        validationRenderCell,
        validationInstance,
        commentRenderCell,
        commentInstance
    }
}

export {
    useEntries,
    useEditingInfo
}