import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useDataProcessingMethodTypeHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({dataProcessingMethodTypeList}) => {
    const hiddenColumns = useDataProcessingMethodTypeHiddenColumns(dataProcessingMethodTypeList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.data_processing_method_type.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.data_processing_method_type.name'}),
        accessor: 'name'
    }, {
        id: 'type',
        Header: intl.formatMessage({id: 'sheet.data_processing_method_type.type'}),
        accessor: 'type'
    }, {
        id: 'ontology_uri',
        Header: intl.formatMessage({id: 'sheet.data_processing_method_type.ontology_uri'}),
        accessor: 'ontology_uri'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.data_processing_method_type.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.data_processing_method_type.description'}),
        accessor: 'description'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'type':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'ontology_uri':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: dataProcessingMethodTypeList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
