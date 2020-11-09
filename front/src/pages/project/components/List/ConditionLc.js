import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useConditionLcHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({conditionLcList}) => {
    const hiddenColumns = useConditionLcHiddenColumns(conditionLcList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.condition_lc.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.condition_lc.name'}),
        accessor: 'name'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.condition_lc.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.condition_lc.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'instrument',
        Header: intl.formatMessage({id: 'sheet.condition_lc.instrument'}),
        accessor: 'instrument'
    }, {
        id: 'column',
        Header: intl.formatMessage({id: 'sheet.condition_lc.column'}),
        accessor: 'column'
    }, {
        id: 'column_type',
        Header: intl.formatMessage({id: 'sheet.condition_lc.column_type'}),
        accessor: 'column_type'
    }, {
        id: 'column_other',
        Header: intl.formatMessage({id: 'sheet.condition_lc.column_other'}),
        accessor: 'column_other'
    }, {
        id: 'column_temperature',
        Header: intl.formatMessage({id: 'sheet.condition_lc.column_temperature'}),
        accessor: 'column_temperature'
    }, {
        id: 'column_pressure',
        Header: intl.formatMessage({id: 'sheet.condition_lc.column_pressure'}),
        accessor: 'column_pressure'
    }, {
        id: 'solvent_a',
        Header: intl.formatMessage({id: 'sheet.condition_lc.solvent_a'}),
        accessor: 'solvent_a'
    }, {
        id: 'solvent_b',
        Header: intl.formatMessage({id: 'sheet.condition_lc.solvent_b'}),
        accessor: 'solvent_b'
    }, {
        id: 'solvent_other',
        Header: intl.formatMessage({id: 'sheet.condition_lc.solvent_other'}),
        accessor: 'solvent_other'
    }, {
        id: 'flow_gradient',
        Header: intl.formatMessage({id: 'sheet.condition_lc.flow_gradient'}),
        accessor: 'flow_gradient'
    }, {
        id: 'flow_rate',
        Header: intl.formatMessage({id: 'sheet.condition_lc.flow_rate'}),
        accessor: 'flow_rate'
    }, {
        id: 'elution_detector',
        Header: intl.formatMessage({id: 'sheet.condition_lc.elution_detector'}),
        accessor: 'elution_detector'
    }, {
        id: 'elution_detector_wave_length_min',
        Header: intl.formatMessage({id: 'sheet.condition_lc.elution_detector_wave_length_min'}),
        accessor: 'elution_detector_wave_length_min'
    }, {
        id: 'elution_detector_wave_length_max',
        Header: intl.formatMessage({id: 'sheet.condition_lc.elution_detector_wave_length_max'}),
        accessor: 'elution_detector_wave_length_max'
    }, {
        id: 'analytical_time',
        Header: intl.formatMessage({id: 'sheet.condition_lc.analytical_time'}),
        accessor: 'analytical_time'
    }, {
        id: 'control_software',
        Header: intl.formatMessage({id: 'sheet.condition_lc.control_software'}),
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
            case 'column':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'column_type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'column_other':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'column_temperature':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'column_pressure':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'solvent_a':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'solvent_b':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'solvent_other':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'flow_gradient':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'flow_rate':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'elution_detector':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'elution_detector_wave_length_min':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'elution_detector_wave_length_max':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'analytical_time':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'control_software':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: conditionLcList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
