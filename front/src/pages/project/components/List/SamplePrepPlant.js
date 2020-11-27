import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useSamplePrepPlantHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({samplePrepPlantList}) => {
    const hiddenColumns = useSamplePrepPlantHiddenColumns(samplePrepPlantList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.name'}),
        accessor: 'name'
    }, {
        id: 'name_ja',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.name_ja'}),
        accessor: 'name_ja'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.reference'}),
        accessor: 'reference'
    }, {
        id: 'growth_condition',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.growth_condition'}),
        accessor: 'growth_condition'
    }, {
        id: 'day_length',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.day_length'}),
        accessor: 'day_length'
    }, {
        id: 'night_length',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.night_length'}),
        accessor: 'night_length'
    }, {
        id: 'humidity',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.humidity'}),
        accessor: 'humidity'
    }, {
        id: 'day_temperature',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.day_temperature'}),
        accessor: 'day_temperature'
    }, {
        id: 'night_temperature',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.night_temperature'}),
        accessor: 'night_temperature'
    }, {
        id: 'temperature',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.temperature'}),
        accessor: 'temperature'
    }, {
        id: 'light_condition',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.light_condition'}),
        accessor: 'light_condition'
    }, {
        id: 'sampling_data',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.sampling_data'}),
        accessor: 'sampling_data'
    }, {
        id: 'sampling_time',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.sampling_time'}),
        accessor: 'sampling_time'
    }, {
        id: 'sampling_location',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.sampling_location'}),
        accessor: 'sampling_location'
    }, {
        id: 'watering_regime',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.watering_regime'}),
        accessor: 'watering_regime'
    }, {
        id: 'nutritional_regime',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.nutritional_regime'}),
        accessor: 'nutritional_regime'
    }, {
        id: 'growth_medium',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.growth_medium'}),
        accessor: 'growth_medium'
    }, {
        id: 'growth_location',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.growth_location'}),
        accessor: 'growth_location'
    }, {
        id: 'plot_design',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.plot_design'}),
        accessor: 'plot_design'
    }, {
        id: 'sowing_date',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.sowing_date'}),
        accessor: 'sowing_date'
    }, {
        id: 'metabolism_quenching_method',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.metabolism_quenching_method'}),
        accessor: 'metabolism_quenching_method'
    }, {
        id: 'sample_storage_method',
        Header: intl.formatMessage({id: 'sheet.sample_prep_plant.sample_storage_method'}),
        accessor: 'sample_storage_method'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'name_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'description_ja':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" labelType="string" value={cell.value}/>
            case 'growth_condition':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'day_length':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'night_length':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'humidity':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'day_temperature':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'night_temperature':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'temperature':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'light_condition':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'sampling_data':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'sampling_time':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'sampling_location':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'watering_regime':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'nutritional_regime':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'growth_medium':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'growth_location':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'plot_design':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'sowing_date':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'metabolism_quenching_method':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'sample_storage_method':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: samplePrepPlantList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
