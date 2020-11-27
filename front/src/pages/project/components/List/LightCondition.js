import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useLightConditionHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({lightConditionList}) => {
    const hiddenColumns = useLightConditionHiddenColumns(lightConditionList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.light_condition.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.light_condition.name'}),
        accessor: 'name'
    }, {
        id: 'light_quality',
        Header: intl.formatMessage({id: 'sheet.light_condition.light_quality'}),
        accessor: 'light_quality'
    }, {
        id: 'light_intensity',
        Header: intl.formatMessage({id: 'sheet.light_condition.light_intensity'}),
        accessor: 'light_intensity'
    }, {
        id: 'peak_wave_length',
        Header: intl.formatMessage({id: 'sheet.light_condition.peak_wave_length'}),
        accessor: 'peak_wave_length'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'light_quality':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'light_intensity':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'peak_wave_length':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: lightConditionList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
