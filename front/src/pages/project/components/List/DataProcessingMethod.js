import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useDataProcessingMethodHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({dataProcessingMethodList}) => {
    const hiddenColumns = useDataProcessingMethodHiddenColumns(dataProcessingMethodList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.name'}),
        accessor: 'name'
    }, {
        id: 'type',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.type'}),
        accessor: 'type'
    }, {
        id: 'method_type',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.method_type'}),
        accessor: 'method_type'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.description'}),
        accessor: 'description'
    }, {
        id: 'software',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.software'}),
        accessor: 'software'
    }, {
        id: 'parameter',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.parameter'}),
        accessor: 'parameter'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.data_processing_method.reference'}),
        accessor: 'reference'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'method_type':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'software':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'parameter':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: dataProcessingMethodList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
