import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useAnalyzedRawFileHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({analyzedRawFileList}) => {
    const hiddenColumns = useAnalyzedRawFileHiddenColumns(analyzedRawFileList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.analyzed_raw_file.id'}),
        accessor: 'id'
    }, {
        id: 'data_analysis_id',
        Header: intl.formatMessage({id: 'sheet.analyzed_raw_file.data_analysis_id'}),
        accessor: 'data_analysis_id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.analyzed_raw_file.name'}),
        accessor: 'name'
    }, {
        id: 'project_id',
        Header: intl.formatMessage({id: 'sheet.analyzed_raw_file.project_id'}),
        accessor: 'project_id'
    }, {
        id: 'category',
        Header: intl.formatMessage({id: 'sheet.analyzed_raw_file.category'}),
        accessor: 'category'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'data_analysis_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'project_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'category':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: analyzedRawFileList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
