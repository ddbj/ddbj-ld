import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useInstrumentHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({instrumentList}) => {
    const hiddenColumns = useInstrumentHiddenColumns(instrumentList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.instrument.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.instrument.name'}),
        accessor: 'name'
    }, {
        id: 'type',
        Header: intl.formatMessage({id: 'sheet.instrument.type'}),
        accessor: 'type'
    }, {
        id: 'supplier',
        Header: intl.formatMessage({id: 'sheet.instrument.supplier'}),
        accessor: 'supplier'
    }, {
        id: 'product_name',
        Header: intl.formatMessage({id: 'sheet.instrument.product_name'}),
        accessor: 'product_name'
    }, {
        id: 'product_id',
        Header: intl.formatMessage({id: 'sheet.instrument.product_id'}),
        accessor: 'product_id'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.instrument.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.instrument.description'}),
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
        data: instrumentList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
