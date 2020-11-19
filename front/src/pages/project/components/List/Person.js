import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {usePersonHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({personList}) => {
    const hiddenColumns = usePersonHiddenColumns(personList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.person.id'}),
        accessor: 'id'
    }, {
        id: 'label',
        Header: intl.formatMessage({id: 'sheet.person.label'}),
        accessor: 'label'
    }, {
        id: 'label_ja',
        Header: intl.formatMessage({id: 'sheet.person.label_ja'}),
        accessor: 'label_ja'
    }, {
        id: 'name_family',
        Header: intl.formatMessage({id: 'sheet.person.name_family'}),
        accessor: 'name_family'
    }, {
        id: 'name_first',
        Header: intl.formatMessage({id: 'sheet.person.name_first'}),
        accessor: 'name_first'
    }, {
        id: 'name_family_ja',
        Header: intl.formatMessage({id: 'sheet.person.name_family_ja'}),
        accessor: 'name_family_ja'
    }, {
        id: 'name_first_ja',
        Header: intl.formatMessage({id: 'sheet.person.name_first_ja'}),
        accessor: 'name_first_ja'
    }, {
        id: 'name_middle',
        Header: intl.formatMessage({id: 'sheet.person.name_middle'}),
        accessor: 'name_middle'
    }, {
        id: 'organisation',
        Header: intl.formatMessage({id: 'sheet.person.organisation'}),
        accessor: 'organisation'
    }, {
        id: 'email',
        Header: intl.formatMessage({id: 'sheet.person.email'}),
        accessor: 'email'
    }, {
        id: 'orcid',
        Header: intl.formatMessage({id: 'sheet.person.orcid'}),
        accessor: 'orcid'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'label':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'label_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'name_family':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'name_first':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'name_family_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'name_first_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'name_middle':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'organisation':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'email':
                return <Value mbGoRefer="" labelType="email" value={cell.value}/>
            case 'orcid':
                return <Value mbGoRefer="" labelType="orcid" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: personList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
