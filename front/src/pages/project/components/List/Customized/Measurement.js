import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import ListTable from '../ListTable'
import {ListViewValue as Value} from '../../Value'
import { useIntl } from 'react-intl'

const List = ({measurementList}) => {
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.measurement.id'}),
        accessor: 'id'
    }, {
        id: 'experiment',
        Header: intl.formatMessage({id: 'sheet.measurement.experiment_name'}),
        accessor: 'experiment'
    }, {
        id: 'sample',
        Header: intl.formatMessage({id: 'sheet.measurement.sample_name'}),
        accessor: 'sample'
    }, {
        id: 'extract_id',
        Header: intl.formatMessage({id: 'sheet.measurement.extract_id'}),
        accessor: 'extract_id'
    }, {
        id: 'extraction_method',
        Header: intl.formatMessage({id: 'sheet.measurement.extraction_method_name'}),
        accessor: 'extraction_method'
    }, {
        id: 'measurement_id',
        Header: intl.formatMessage({id: 'sheet.measurement.measurement_id'}),
        accessor: 'measurement_id'
    }, {
        id: 'measurement_method',
        Header: intl.formatMessage({id: 'sheet.measurement.measurement_method_name'}),
        accessor: 'measurement_method'
    }]), [])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'experiment':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'sample':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'extract_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'extraction_method':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'measurement_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'measurement_method':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: measurementList,
        initialState: {}
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
