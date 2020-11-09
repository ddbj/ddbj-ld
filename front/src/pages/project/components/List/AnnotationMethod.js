import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useAnnotationMethodHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({annotationMethodList}) => {
    const hiddenColumns = useAnnotationMethodHiddenColumns(annotationMethodList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.annotation_method.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.annotation_method.name'}),
        accessor: 'name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.annotation_method.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.annotation_method.description'}),
        accessor: 'description'
    }, {
        id: 'software',
        Header: intl.formatMessage({id: 'sheet.annotation_method.software'}),
        accessor: 'software'
    }, {
        id: 'parameters',
        Header: intl.formatMessage({id: 'sheet.annotation_method.parameters'}),
        accessor: 'parameters'
    }, {
        id: 'spectral_library',
        Header: intl.formatMessage({id: 'sheet.annotation_method.spectral_library'}),
        accessor: 'spectral_library'
    }, {
        id: 'evidence',
        Header: intl.formatMessage({id: 'sheet.annotation_method.evidence'}),
        accessor: 'evidence'
    }, {
        id: 'annotation_type',
        Header: intl.formatMessage({id: 'sheet.annotation_method.annotation_type'}),
        accessor: 'annotation_type'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.annotation_method.reference'}),
        accessor: 'reference'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'software':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'parameters':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'spectral_library':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'evidence':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'annotation_type':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: annotationMethodList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
