import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useFileHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({fileList}) => {
    const hiddenColumns = useFileHiddenColumns(fileList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.file.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.file.name'}),
        accessor: 'name'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.file.description'}),
        accessor: 'description'
    }, {
        id: 'local_folder_path',
        Header: intl.formatMessage({id: 'sheet.file.local_folder_path'}),
        accessor: 'local_folder_path'
    }, {
        id: 'file_name',
        Header: intl.formatMessage({id: 'sheet.file.file_name'}),
        accessor: 'file_name'
    }, {
        id: 'file_format',
        Header: intl.formatMessage({id: 'sheet.file.file_format'}),
        accessor: 'file_format'
    }, {
        id: 'download_url',
        Header: intl.formatMessage({id: 'sheet.file.download_url'}),
        accessor: 'download_url'
    }, {
        id: 'md5',
        Header: intl.formatMessage({id: 'sheet.file.md5'}),
        accessor: 'md5'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'local_folder_path':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'file_name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'file_format':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'download_url':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'md5':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: fileList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
