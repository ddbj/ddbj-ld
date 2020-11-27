import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useAnalyzedResultFileHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({analyzedResultFileList}) => {
    const hiddenColumns = useAnalyzedResultFileHiddenColumns(analyzedResultFileList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.analyzed_result_file.id'}),
        accessor: 'id'
    }, {
        id: 'data_analysis_id',
        Header: intl.formatMessage({id: 'sheet.analyzed_result_file.data_analysis_id'}),
        accessor: 'data_analysis_id'
    }, {
        id: 'data_type',
        Header: intl.formatMessage({id: 'sheet.analyzed_result_file.data_type'}),
        accessor: 'data_type'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.analyzed_result_file.name'}),
        accessor: 'name'
    }, {
        id: 'download_url',
        Header: intl.formatMessage({id: 'sheet.analyzed_result_file.download_url'}),
        accessor: 'download_url'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'data_analysis_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'data_analysis_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'data_type_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'data_type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'local_folder_path':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'file_name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'file_format':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'download_url':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'md5':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: analyzedResultFileList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
