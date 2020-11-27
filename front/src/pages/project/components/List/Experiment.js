import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useExperimentHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({experimentList}) => {
    const hiddenColumns = useExperimentHiddenColumns(experimentList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.experiment.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.experiment.name'}),
        accessor: 'name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.experiment.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.experiment.description'}),
        accessor: 'description'
    }, {
        id: 'measurement_type',
        Header: intl.formatMessage({id: 'sheet.experiment.measurement_type'}),
        accessor: 'measurement_type'
    }, {
        id: 'technology_type',
        Header: intl.formatMessage({id: 'sheet.experiment.technology_type'}),
        accessor: 'technology_type'
    }, {
        id: 'technology_platform',
        Header: intl.formatMessage({id: 'sheet.experiment.technology_platform'}),
        accessor: 'technology_platform'
    }, {
        id: 'date_time',
        Header: intl.formatMessage({id: 'sheet.experiment.date_time'}),
        accessor: 'date_time'
    }, {
        id: 'person_experimenter',
        Header: intl.formatMessage({id: 'sheet.experiment.person_experimenter'}),
        accessor: 'person_experimenter'
    }, {
        id: 'experimental_design',
        Header: intl.formatMessage({id: 'sheet.experiment.experimental_design'}),
        accessor: 'experimental_design'
    }, {
        id: 'data_analysis_id',
        Header: intl.formatMessage({id: 'sheet.experiment.data_analysis_id'}),
        accessor: 'data_analysis_id'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.experiment.reference'}),
        accessor: 'reference'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'measurement_type':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'technology_type':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'technology_platform':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'date_time':
                return <Value mbGoRefer="" labelType="date" value={cell.value}/>
            case 'person_experimenter':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'experimental_design':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'data_analysis_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="inner" labelType="text" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: experimentList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
