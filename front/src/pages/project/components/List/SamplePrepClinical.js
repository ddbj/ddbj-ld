import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useSamplePrepClinicalHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({samplePrepClinicalList}) => {
    const hiddenColumns = useSamplePrepClinicalHiddenColumns(samplePrepClinicalList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.sample_prep_clinical.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.sample_prep_clinical.name'}),
        accessor: 'name'
    }, {
        id: 'name_ja',
        Header: intl.formatMessage({id: 'sheet.sample_prep_clinical.name_ja'}),
        accessor: 'name_ja'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.sample_prep_clinical.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.sample_prep_clinical.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.sample_prep_clinical.reference'}),
        accessor: 'reference'
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
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: samplePrepClinicalList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
