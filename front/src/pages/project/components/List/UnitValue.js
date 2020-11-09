import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useUnitValueHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({unitValueList}) => {
    const hiddenColumns = useUnitValueHiddenColumns(unitValueList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.unit_value.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.unit_value.name'}),
        accessor: 'name'
    }, {
        id: 'type',
        Header: intl.formatMessage({id: 'sheet.unit_value.type'}),
        accessor: 'type'
    }, {
        id: 'value',
        Header: intl.formatMessage({id: 'sheet.unit_value.value'}),
        accessor: 'value'
    }, {
        id: 'unit',
        Header: intl.formatMessage({id: 'sheet.unit_value.unit'}),
        accessor: 'unit'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'value':
                return <Value mbGoRefer="" labelType="float" value={cell.value}/>
            case 'unit':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: unitValueList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
