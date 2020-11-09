import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useDataPreprocessingHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({dataPreprocessingList}) => {
    const hiddenColumns = useDataPreprocessingHiddenColumns(dataPreprocessingList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.name'}),
        accessor: 'name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.description'}),
        accessor: 'description'
    }, {
        id: 'general',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.general'}),
        accessor: 'general'
    }, {
        id: 'peak_detection',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.peak_detection'}),
        accessor: 'peak_detection'
    }, {
        id: 'peak_alignment',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.peak_alignment'}),
        accessor: 'peak_alignment'
    }, {
        id: 'spectral_extraction',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.spectral_extraction'}),
        accessor: 'spectral_extraction'
    }, {
        id: 'retention_time_correction',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.retention_time_correction'}),
        accessor: 'retention_time_correction'
    }, {
        id: 'summarisation',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.summarisation'}),
        accessor: 'summarisation'
    }, {
        id: 'normalisation',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.normalisation'}),
        accessor: 'normalisation'
    }, {
        id: 'data_transformation',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.data_transformation'}),
        accessor: 'data_transformation'
    }, {
        id: 'scaling',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.scaling'}),
        accessor: 'scaling'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.data_preprocessing.reference'}),
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
            case 'general':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'peak_detection':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'peak_alignment':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'spectral_extraction':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'retention_time_correction':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'summarisation':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'normalisation':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'data_transformation':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'scaling':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: dataPreprocessingList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
