import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useOrganisationHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({organisationList}) => {
    const hiddenColumns = useOrganisationHiddenColumns(organisationList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.organisation.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.organisation.name'}),
        accessor: 'name'
    }, {
        id: 'name_ja',
        Header: intl.formatMessage({id: 'sheet.organisation.name_ja'}),
        accessor: 'name_ja'
    }, {
        id: 'abbreviation',
        Header: intl.formatMessage({id: 'sheet.organisation.abbreviation'}),
        accessor: 'abbreviation'
    }, {
        id: 'homepage',
        Header: intl.formatMessage({id: 'sheet.organisation.homepage'}),
        accessor: 'homepage'
    }, {
        id: 'address',
        Header: intl.formatMessage({id: 'sheet.organisation.address'}),
        accessor: 'address'
    }, {
        id: 'address_ja',
        Header: intl.formatMessage({id: 'sheet.organisation.address_ja'}),
        accessor: 'address_ja'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.organisation.description'}),
        accessor: 'description'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'name_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'abbreviation':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'homepage':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'address':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'address_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: organisationList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
