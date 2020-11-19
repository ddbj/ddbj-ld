import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useMeasurementHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({measurementList}) => {
    const hiddenColumns = useMeasurementHiddenColumns(measurementList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.measurement.id'}),
        accessor: 'id'
    }, {
        id: 'experiment_id',
        Header: intl.formatMessage({id: 'sheet.measurement.experiment_id'}),
        accessor: 'experiment_id'
    }, {
        id: 'experiment_name',
        Header: intl.formatMessage({id: 'sheet.measurement.experiment_name'}),
        accessor: 'experiment_name'
    }, {
        id: 'sample_id',
        Header: intl.formatMessage({id: 'sheet.measurement.sample_id'}),
        accessor: 'sample_id'
    }, {
        id: 'sample_name',
        Header: intl.formatMessage({id: 'sheet.measurement.sample_name'}),
        accessor: 'sample_name'
    }, {
        id: 'extract_id',
        Header: intl.formatMessage({id: 'sheet.measurement.extract_id'}),
        accessor: 'extract_id'
    }, {
        id: 'extraction_method_id',
        Header: intl.formatMessage({id: 'sheet.measurement.extraction_method_id'}),
        accessor: 'extraction_method_id'
    }, {
        id: 'extraction_method_name',
        Header: intl.formatMessage({id: 'sheet.measurement.extraction_method_name'}),
        accessor: 'extraction_method_name'
    }, {
        id: 'measurement_id',
        Header: intl.formatMessage({id: 'sheet.measurement.measurement_id'}),
        accessor: 'measurement_id'
    }, {
        id: 'measurement_method_id',
        Header: intl.formatMessage({id: 'sheet.measurement.measurement_method_id'}),
        accessor: 'measurement_method_id'
    }, {
        id: 'measurement_method_name',
        Header: intl.formatMessage({id: 'sheet.measurement.measurement_method_name'}),
        accessor: 'measurement_method_name'
    }, {
        id: 'date_time',
        Header: intl.formatMessage({id: 'sheet.measurement.date_time'}),
        accessor: 'date_time'
    }, {
        id: 'person_experimenter',
        Header: intl.formatMessage({id: 'sheet.measurement.person_experimenter'}),
        accessor: 'person_experimenter'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.measurement.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.measurement.description'}),
        accessor: 'description'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'experiment_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'experiment_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'sample_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'sample_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'extract_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'extraction_method_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'extraction_method_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'measurement_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'measurement_method_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'measurement_method_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'date_time':
                return <Value mbGoRefer="" labelType="date" value={cell.value}/>
            case 'person_experimenter':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
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
        data: measurementList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
