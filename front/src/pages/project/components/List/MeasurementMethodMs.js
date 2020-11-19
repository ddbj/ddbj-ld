import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useMeasurementMethodMsHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({measurementMethodMsList}) => {
    const hiddenColumns = useMeasurementMethodMsHiddenColumns(measurementMethodMsList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.name'}),
        accessor: 'name'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'ms_condition_id',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.ms_condition_id'}),
        accessor: 'ms_condition_id'
    }, {
        id: 'ms_condition_name',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.ms_condition_name'}),
        accessor: 'ms_condition_name'
    }, {
        id: 'other_condition_id',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.other_condition_id'}),
        accessor: 'other_condition_id'
    }, {
        id: 'other_condition_name',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.other_condition_name'}),
        accessor: 'other_condition_name'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.measurement_method_ms.reference'}),
        accessor: 'reference'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'ms_condition_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'ms_condition_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'other_condition_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'other_condition_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: measurementMethodMsList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
