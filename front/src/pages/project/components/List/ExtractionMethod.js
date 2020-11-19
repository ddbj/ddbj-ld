import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useExtractionMethodHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({extractionMethodList}) => {
    const hiddenColumns = useExtractionMethodHiddenColumns(extractionMethodList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.extraction_method.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.extraction_method.name'}),
        accessor: 'name'
    }, {
        id: 'name_ja',
        Header: intl.formatMessage({id: 'sheet.extraction_method.name_ja'}),
        accessor: 'name_ja'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.extraction_method.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.extraction_method.description'}),
        accessor: 'description'
    }, {
        id: 'internal_standard',
        Header: intl.formatMessage({id: 'sheet.extraction_method.internal_standard'}),
        accessor: 'internal_standard'
    }, {
        id: 'derivatisation',
        Header: intl.formatMessage({id: 'sheet.extraction_method.derivatisation'}),
        accessor: 'derivatisation'
    }, {
        id: 'extract_concentration',
        Header: intl.formatMessage({id: 'sheet.extraction_method.extract_concentration'}),
        accessor: 'extract_concentration'
    }, {
        id: 'extract_storage_method',
        Header: intl.formatMessage({id: 'sheet.extraction_method.extract_storage_method'}),
        accessor: 'extract_storage_method'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.extraction_method.reference'}),
        accessor: 'reference'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'name_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'internal_standard':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'derivatisation':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'extract_concentration':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'extract_storage_method':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: extractionMethodList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
