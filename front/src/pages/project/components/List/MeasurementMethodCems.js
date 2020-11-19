import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useMeasurementMethodCemsHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({measurementMethodCemsList}) => {
    const hiddenColumns = useMeasurementMethodCemsHiddenColumns(measurementMethodCemsList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.name'}),
        accessor: 'name'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'ce_condition_id',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.ce_condition_id'}),
        accessor: 'ce_condition_id'
    }, {
        id: 'ce_condition_name',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.ce_condition_name'}),
        accessor: 'ce_condition_name'
    }, {
        id: 'ms_condition_id',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.ms_condition_id'}),
        accessor: 'ms_condition_id'
    }, {
        id: 'ms_condition_name',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.ms_condition_name'}),
        accessor: 'ms_condition_name'
    }, {
        id: 'other_condition_id',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.other_condition_id'}),
        accessor: 'other_condition_id'
    }, {
        id: 'other_condition_name',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.other_condition_name'}),
        accessor: 'other_condition_name'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.measurement_method_cems.reference'}),
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
            case 'ce_condition_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'ce_condition_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
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
        data: measurementMethodCemsList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
