import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useSoftwareHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({softwareList}) => {
    const hiddenColumns = useSoftwareHiddenColumns(softwareList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.software.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.software.name'}),
        accessor: 'name'
    }, {
        id: 'version',
        Header: intl.formatMessage({id: 'sheet.software.version'}),
        accessor: 'version'
    }, {
        id: 'supplier',
        Header: intl.formatMessage({id: 'sheet.software.supplier'}),
        accessor: 'supplier'
    }, {
        id: 'product_id',
        Header: intl.formatMessage({id: 'sheet.software.product_id'}),
        accessor: 'product_id'
    }, {
        id: 'available_url',
        Header: intl.formatMessage({id: 'sheet.software.available_url'}),
        accessor: 'available_url'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'version':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'supplier':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'product_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'available_url':
                return <Value mbGoRefer="" labelType="url" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: softwareList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
