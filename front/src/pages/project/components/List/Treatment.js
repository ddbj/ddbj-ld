import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useTreatmentHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({treatmentList}) => {
    const hiddenColumns = useTreatmentHiddenColumns(treatmentList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.treatment.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.treatment.name'}),
        accessor: 'name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.treatment.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.treatment.description'}),
        accessor: 'description'
    }, {
        id: 'concentration',
        Header: intl.formatMessage({id: 'sheet.treatment.concentration'}),
        accessor: 'concentration'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'concentration':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: treatmentList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
