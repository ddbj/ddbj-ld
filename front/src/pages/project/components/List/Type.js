import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useTypeHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({typeList}) => {
    const hiddenColumns = useTypeHiddenColumns(typeList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'unit_value',
        Header: intl.formatMessage({id: 'sheet.type.unit_value'}),
        accessor: 'unit_value'
    }, {
        id: 'terms',
        Header: intl.formatMessage({id: 'sheet.type.terms'}),
        accessor: 'terms'
    }, {
        id: 'instrument_type',
        Header: intl.formatMessage({id: 'sheet.type.instrument_type'}),
        accessor: 'instrument_type'
    }, {
        id: 'column_type',
        Header: intl.formatMessage({id: 'sheet.type.column_type'}),
        accessor: 'column_type'
    }, {
        id: 'ms_instrument_type',
        Header: intl.formatMessage({id: 'sheet.type.ms_instrument_type'}),
        accessor: 'ms_instrument_type'
    }, {
        id: 'ion_source',
        Header: intl.formatMessage({id: 'sheet.type.ion_source'}),
        accessor: 'ion_source'
    }, {
        id: 'ionisation_polarity',
        Header: intl.formatMessage({id: 'sheet.type.ionisation_polarity'}),
        accessor: 'ionisation_polarity'
    }, {
        id: 'scan_type',
        Header: intl.formatMessage({id: 'sheet.type.scan_type'}),
        accessor: 'scan_type'
    }, {
        id: 'msn_aqcuisition_type',
        Header: intl.formatMessage({id: 'sheet.type.msn_aqcuisition_type'}),
        accessor: 'msn_aqcuisition_type'
    }, {
        id: 'fragmentation_method',
        Header: intl.formatMessage({id: 'sheet.type.fragmentation_method'}),
        accessor: 'fragmentation_method'
    }, {
        id: 'data_type',
        Header: intl.formatMessage({id: 'sheet.type.data_type'}),
        accessor: 'data_type'
    }, {
        id: 'data_processing_type',
        Header: intl.formatMessage({id: 'sheet.type.data_processing_type'}),
        accessor: 'data_processing_type'
    }, {
        id: 'data_processing_method_type',
        Header: intl.formatMessage({id: 'sheet.type.data_processing_method_type'}),
        accessor: 'data_processing_method_type'
    }, {
        id: 'annotation_type',
        Header: intl.formatMessage({id: 'sheet.type.annotation_type'}),
        accessor: 'annotation_type'
    }, {
        id: 'data_category',
        Header: intl.formatMessage({id: 'sheet.type.data_category'}),
        accessor: 'data_category'
    }, {
        id: 'gc_column_type',
        Header: intl.formatMessage({id: 'sheet.type.gc_column_type'}),
        accessor: 'gc_column_type'
    }, {
        id: 'lc_column_type',
        Header: intl.formatMessage({id: 'sheet.type.lc_column_type'}),
        accessor: 'lc_column_type'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'unit_value':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'terms':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'instrument_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'column_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'ms_instrument_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'ion_source':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'ionisation_polarity':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'scan_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'msn_aqcuisition_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'fragmentation_method':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'data_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'data_processing_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'data_processing_method_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'annotation_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'data_category':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'gc_column_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'lc_column_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: typeList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
