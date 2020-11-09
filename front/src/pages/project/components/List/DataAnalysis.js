import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useDataAnalysisHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({dataAnalysisList}) => {
    const hiddenColumns = useDataAnalysisHiddenColumns(dataAnalysisList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.data_analysis.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.data_analysis.name'}),
        accessor: 'name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.data_analysis.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.data_analysis.description'}),
        accessor: 'description'
    }, {
        id: 'data_preprocessing',
        Header: intl.formatMessage({id: 'sheet.data_analysis.data_preprocessing'}),
        accessor: 'data_preprocessing'
    }, {
        id: 'statictical_analysis',
        Header: intl.formatMessage({id: 'sheet.data_analysis.statictical_analysis'}),
        accessor: 'statictical_analysis'
    }, {
        id: 'univariate_analysis',
        Header: intl.formatMessage({id: 'sheet.data_analysis.univariate_analysis'}),
        accessor: 'univariate_analysis'
    }, {
        id: 'multivariate_analysis',
        Header: intl.formatMessage({id: 'sheet.data_analysis.multivariate_analysis'}),
        accessor: 'multivariate_analysis'
    }, {
        id: 'visualisation',
        Header: intl.formatMessage({id: 'sheet.data_analysis.visualisation'}),
        accessor: 'visualisation'
    }, {
        id: 'annotation_method',
        Header: intl.formatMessage({id: 'sheet.data_analysis.annotation_method'}),
        accessor: 'annotation_method'
    }, {
        id: 'recommended_decimal_place_mz',
        Header: intl.formatMessage({id: 'sheet.data_analysis.recommended_decimal_place_mz'}),
        accessor: 'recommended_decimal_place_mz'
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
            case 'data_preprocessing':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'statictical_analysis':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'univariate_analysis':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'multivariate_analysis':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'visualisation':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'annotation_method':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'recommended_decimal_place_mz':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: dataAnalysisList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
