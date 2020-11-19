import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useConditionNmrHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({conditionNmrList}) => {
    const hiddenColumns = useConditionNmrHiddenColumns(conditionNmrList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.condition_nmr.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.condition_nmr.name'}),
        accessor: 'name'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.condition_nmr.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.condition_nmr.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'instrument',
        Header: intl.formatMessage({id: 'sheet.condition_nmr.instrument'}),
        accessor: 'instrument'
    }, {
        id: 'control_software',
        Header: intl.formatMessage({id: 'sheet.condition_nmr.control_software'}),
        accessor: 'control_software'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'instrument':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'control_software':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: conditionNmrList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
