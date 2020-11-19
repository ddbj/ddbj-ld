import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useConditionMsHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({conditionMsList}) => {
    const hiddenColumns = useConditionMsHiddenColumns(conditionMsList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.condition_ms.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.condition_ms.name'}),
        accessor: 'name'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.condition_ms.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.condition_ms.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'instrument',
        Header: intl.formatMessage({id: 'sheet.condition_ms.instrument'}),
        accessor: 'instrument'
    }, {
        id: 'ms_instrument_type',
        Header: intl.formatMessage({id: 'sheet.condition_ms.ms_instrument_type'}),
        accessor: 'ms_instrument_type'
    }, {
        id: 'ion_source',
        Header: intl.formatMessage({id: 'sheet.condition_ms.ion_source'}),
        accessor: 'ion_source'
    }, {
        id: 'ionization_polarity',
        Header: intl.formatMessage({id: 'sheet.condition_ms.ionization_polarity'}),
        accessor: 'ionization_polarity'
    }, {
        id: 'ionization_energy',
        Header: intl.formatMessage({id: 'sheet.condition_ms.ionization_energy'}),
        accessor: 'ionization_energy'
    }, {
        id: 'scan_type',
        Header: intl.formatMessage({id: 'sheet.condition_ms.scan_type'}),
        accessor: 'scan_type'
    }, {
        id: 'full_scan_mz_range',
        Header: intl.formatMessage({id: 'sheet.condition_ms.full_scan_mz_range'}),
        accessor: 'full_scan_mz_range'
    }, {
        id: 'ms_acquisition_rate',
        Header: intl.formatMessage({id: 'sheet.condition_ms.ms_acquisition_rate'}),
        accessor: 'ms_acquisition_rate'
    }, {
        id: 'msn_acquisition_method',
        Header: intl.formatMessage({id: 'sheet.condition_ms.msn_acquisition_method'}),
        accessor: 'msn_acquisition_method'
    }, {
        id: 'scan_program_details',
        Header: intl.formatMessage({id: 'sheet.condition_ms.scan_program_details'}),
        accessor: 'scan_program_details'
    }, {
        id: 'resolution',
        Header: intl.formatMessage({id: 'sheet.condition_ms.resolution'}),
        accessor: 'resolution'
    }, {
        id: 'mz_accuracy_full_scan',
        Header: intl.formatMessage({id: 'sheet.condition_ms.mz_accuracy_full_scan'}),
        accessor: 'mz_accuracy_full_scan'
    }, {
        id: 'mz_accuracy_msn_scan',
        Header: intl.formatMessage({id: 'sheet.condition_ms.mz_accuracy_msn_scan'}),
        accessor: 'mz_accuracy_msn_scan'
    }, {
        id: 'capillary_temperature',
        Header: intl.formatMessage({id: 'sheet.condition_ms.capillary_temperature'}),
        accessor: 'capillary_temperature'
    }, {
        id: 'collision_energy',
        Header: intl.formatMessage({id: 'sheet.condition_ms.collision_energy'}),
        accessor: 'collision_energy'
    }, {
        id: 'ion_source_temperature',
        Header: intl.formatMessage({id: 'sheet.condition_ms.ion_source_temperature'}),
        accessor: 'ion_source_temperature'
    }, {
        id: 'ion_spray_voltage',
        Header: intl.formatMessage({id: 'sheet.condition_ms.ion_spray_voltage'}),
        accessor: 'ion_spray_voltage'
    }, {
        id: 'fragmentation_method',
        Header: intl.formatMessage({id: 'sheet.condition_ms.fragmentation_method'}),
        accessor: 'fragmentation_method'
    }, {
        id: 'retention_index_method',
        Header: intl.formatMessage({id: 'sheet.condition_ms.retention_index_method'}),
        accessor: 'retention_index_method'
    }, {
        id: 'desolvation_temperature',
        Header: intl.formatMessage({id: 'sheet.condition_ms.desolvation_temperature'}),
        accessor: 'desolvation_temperature'
    }, {
        id: 'sheath_gas',
        Header: intl.formatMessage({id: 'sheet.condition_ms.sheath_gas'}),
        accessor: 'sheath_gas'
    }, {
        id: 'control_software',
        Header: intl.formatMessage({id: 'sheet.condition_ms.control_software'}),
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
            case 'ms_instrument_type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'ion_source':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'ionization_polarity':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'ionization_energy':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'scan_type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'full_scan_mz_range':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'ms_acquisition_rate':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'msn_acquisition_method':
                return <Value mbGoRefer="no" labelType="text" value={cell.value}/>
            case 'scan_program_details':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'resolution':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'mz_accuracy_full_scan':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'mz_accuracy_msn_scan':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'capillary_temperature':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'collision_energy':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'ion_source_temperature':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'ion_spray_voltage':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'fragmentation_method':
                return <Value mbGoRefer="no" labelType="text" value={cell.value}/>
            case 'retention_index_method':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'desolvation_temperature':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'sheath_gas':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'control_software':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: conditionMsList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
