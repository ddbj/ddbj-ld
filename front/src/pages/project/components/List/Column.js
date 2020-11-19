import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useColumnHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({columnList}) => {
    const hiddenColumns = useColumnHiddenColumns(columnList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.column.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.column.name'}),
        accessor: 'name'
    }, {
        id: 'type',
        Header: intl.formatMessage({id: 'sheet.column.type'}),
        accessor: 'type'
    }, {
        id: 'supplier',
        Header: intl.formatMessage({id: 'sheet.column.supplier'}),
        accessor: 'supplier'
    }, {
        id: 'product_name',
        Header: intl.formatMessage({id: 'sheet.column.product_name'}),
        accessor: 'product_name'
    }, {
        id: 'product_id',
        Header: intl.formatMessage({id: 'sheet.column.product_id'}),
        accessor: 'product_id'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.column.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.column.description'}),
        accessor: 'description'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'supplier':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'product_name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'product_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: columnList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
