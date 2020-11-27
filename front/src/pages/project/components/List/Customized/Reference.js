import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import ListTable from '../ListTable'
import {ListViewValue as Value} from '../../Value'
import { useIntl } from 'react-intl'
import {useLocale} from "../../../../../hooks/i18n";

const List = ({referenceList}) => {
    const intl = useIntl()
    const locale = useLocale()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.reference.id'}),
        accessor: 'id'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.reference'}),
        accessor: 'reference'
    }]), [locale])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" viewType="single" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: referenceList
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
