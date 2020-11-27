import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useSamplePrepAnimalHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({samplePrepAnimalList}) => {
    const hiddenColumns = useSamplePrepAnimalHiddenColumns(samplePrepAnimalList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.name'}),
        accessor: 'name'
    }, {
        id: 'name_ja',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.name_ja'}),
        accessor: 'name_ja'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.reference'}),
        accessor: 'reference'
    }, {
        id: 'day_length',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.day_length'}),
        accessor: 'day_length'
    }, {
        id: 'night_length',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.night_length'}),
        accessor: 'night_length'
    }, {
        id: 'humidity',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.humidity'}),
        accessor: 'humidity'
    }, {
        id: 'day_temperature',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.day_temperature'}),
        accessor: 'day_temperature'
    }, {
        id: 'night_temperature',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.night_temperature'}),
        accessor: 'night_temperature'
    }, {
        id: 'temperature',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.temperature'}),
        accessor: 'temperature'
    }, {
        id: 'light_condition',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.light_condition'}),
        accessor: 'light_condition'
    }, {
        id: 'sampling_date',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.sampling_date'}),
        accessor: 'sampling_date'
    }, {
        id: 'sampling_time',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.sampling_time'}),
        accessor: 'sampling_time'
    }, {
        id: 'sampling_location',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.sampling_location'}),
        accessor: 'sampling_location'
    }, {
        id: 'breeding_condition',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.breeding_condition'}),
        accessor: 'breeding_condition'
    }, {
        id: 'acclimation_duration',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.acclimation_duration'}),
        accessor: 'acclimation_duration'
    }, {
        id: 'cage_type',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.cage_type'}),
        accessor: 'cage_type'
    }, {
        id: 'cage_cleaning_frequency',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.cage_cleaning_frequency'}),
        accessor: 'cage_cleaning_frequency'
    }, {
        id: 'feeding',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.feeding'}),
        accessor: 'feeding'
    }, {
        id: 'food_manufacturer',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.food_manufacturer'}),
        accessor: 'food_manufacturer'
    }, {
        id: 'water_access',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.water_access'}),
        accessor: 'water_access'
    }, {
        id: 'water_type',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.water_type'}),
        accessor: 'water_type'
    }, {
        id: 'water_quality',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.water_quality'}),
        accessor: 'water_quality'
    }, {
        id: 'enthanasia_method',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.enthanasia_method'}),
        accessor: 'enthanasia_method'
    }, {
        id: 'tissue_collection_method',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.tissue_collection_method'}),
        accessor: 'tissue_collection_method'
    }, {
        id: 'tissue_processing_method',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.tissue_processing_method'}),
        accessor: 'tissue_processing_method'
    }, {
        id: 'veterinary_treatment',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.veterinary_treatment'}),
        accessor: 'veterinary_treatment'
    }, {
        id: 'anesthesia',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.anesthesia'}),
        accessor: 'anesthesia'
    }, {
        id: 'metabolism_quenching_method',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.metabolism_quenching_method'}),
        accessor: 'metabolism_quenching_method'
    }, {
        id: 'sample_storage_method',
        Header: intl.formatMessage({id: 'sheet.sample_prep_animal.sample_storage_method'}),
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
            case 'sampling_date':
                return <Value mbGoRefer="" labelType="date" value={cell.value}/>
            case 'sampling_time':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'sampling_location':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'breeding_condition':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'acclimation_duration':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'cage_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'cage_cleaning_frequency':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'feeding':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'food_manufacturer':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'water_access':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'water_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'water_quality':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'enthanasia_method':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'tissue_collection_method':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'tissue_processing_method':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'veterinary_treatment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'anesthesia':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'metabolism_quenching_method':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'sample_storage_method':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: samplePrepAnimalList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
