import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useRawDataFileHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({rawDataFileList}) => {
    const hiddenColumns = useRawDataFileHiddenColumns(rawDataFileList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.id'}),
        accessor: 'id'
    }, {
        id: 'measurement_id',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.measurement_id'}),
        accessor: 'measurement_id'
    }, {
        id: 'data_type_id',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.data_type_id'}),
        accessor: 'data_type_id'
    }, {
        id: 'data_type',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.data_type'}),
        accessor: 'data_type'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.name'}),
        accessor: 'name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.description'}),
        accessor: 'description'
    }, {
        id: 'local_folder_path',
        Header: intl.formatMessage({id: 'sheet.raw_data_file.local_folder_path'}),
        accessor: 'local_folder_path'
    }, {
        id: 'download_url',
        Header: intl.formatMessage({id: 'sheet.raw_data_download_file.download_url'}),
        accessor: 'download_url'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'measurement_id':
                // FIXME 応急処置、URLに紐付けるID形態がバラバラのため将来的に統一したい
                const resourceId = cell.value[0].content
                const value = [{
                    content: resourceId,
                    option: {
                        ...cell.value[0].option,
                        resourceId
                    }
                }]

                return <Value mbGoRefer="inner" labelType="string" value={value}/>
            case 'data_type_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'data_type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'local_folder_path':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'file_name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'file_format':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'download_url':
                return <Value mbGoRefer="link" labelType="uri" value={cell.value}/>
            case 'md5':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: rawDataFileList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List

