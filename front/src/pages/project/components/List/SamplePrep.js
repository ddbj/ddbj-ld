import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useSamplePrepHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({samplePrepList}) => {
    const hiddenColumns = useSamplePrepHiddenColumns(samplePrepList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.sample_prep.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.sample_prep.name'}),
        accessor: 'name'
    }, {
        id: 'category',
        Header: intl.formatMessage({id: 'sheet.sample_prep.category'}),
        accessor: 'category'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.sample_prep.comment'}),
        accessor: 'comment'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'category':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: samplePrepList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
