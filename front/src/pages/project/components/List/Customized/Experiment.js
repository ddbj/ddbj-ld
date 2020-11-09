import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import ListTable from '../ListTable'
import {ListViewValue as Value} from '../../Value'
import { useIntl } from 'react-intl'
import {useLocale} from "../../../../../hooks/i18n";

const List = ({experimentList}) => {
    const intl = useIntl()
    const locale = useLocale()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.experiment.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.experiment.name'}),
        accessor: 'name'
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
        id: 'experimental_design',
        Header: intl.formatMessage({id: 'sheet.experiment.experimental_design'}),
        accessor: 'experimental_design'
    }]), [locale])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'measurement_type':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'technology_type':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'technology_platform':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'experimental_design':
                return <Value mbGoRefer="inner" labelType="text" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: experimentList,
        initialState: {}
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
