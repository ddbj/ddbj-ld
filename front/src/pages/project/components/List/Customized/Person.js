import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import ListTable from '../ListTable'
import {ListViewValue as Value} from '../../Value'
import { useIntl } from 'react-intl'
import {useLocale} from "../../../../../hooks/i18n";

const List = ({personList}) => {
    const intl = useIntl()
    const locale = useLocale()

    const columns = useMemo(() => ([{
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.person'}),
        accessor: 'name'
    }, {
        id: 'affiliation',
        Header: intl.formatMessage({id: 'sheet.organisation'}),
        accessor: 'affiliation'
    }]), [locale])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'name':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'affiliation':
                return <Value mbGoRefer="link" labelType="text" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: personList,
        initialState: {}
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
