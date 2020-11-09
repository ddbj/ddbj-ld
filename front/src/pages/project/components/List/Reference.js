import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useReferenceHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({referenceList}) => {
    const hiddenColumns = useReferenceHiddenColumns(referenceList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.reference.id'}),
        accessor: 'id'
    }, {
        id: 'citation_label',
        Header: intl.formatMessage({id: 'sheet.reference.citation_label'}),
        accessor: 'citation_label'
    }, {
        id: 'title',
        Header: intl.formatMessage({id: 'sheet.reference.title'}),
        accessor: 'title'
    }, {
        id: 'title_ja',
        Header: intl.formatMessage({id: 'sheet.reference.title_ja'}),
        accessor: 'title_ja'
    }, {
        id: 'authors',
        Header: intl.formatMessage({id: 'sheet.reference.authors'}),
        accessor: 'authors'
    }, {
        id: 'authors_ja',
        Header: intl.formatMessage({id: 'sheet.reference.authors_ja'}),
        accessor: 'authors_ja'
    }, {
        id: 'journal',
        Header: intl.formatMessage({id: 'sheet.reference.journal'}),
        accessor: 'journal'
    }, {
        id: 'journal_ja',
        Header: intl.formatMessage({id: 'sheet.reference.journal_ja'}),
        accessor: 'journal_ja'
    }, {
        id: 'year',
        Header: intl.formatMessage({id: 'sheet.reference.year'}),
        accessor: 'year'
    }, {
        id: 'volume',
        Header: intl.formatMessage({id: 'sheet.reference.volume'}),
        accessor: 'volume'
    }, {
        id: 'issue',
        Header: intl.formatMessage({id: 'sheet.reference.issue'}),
        accessor: 'issue'
    }, {
        id: 'pages',
        Header: intl.formatMessage({id: 'sheet.reference.pages'}),
        accessor: 'pages'
    }, {
        id: 'doi',
        Header: intl.formatMessage({id: 'sheet.reference.doi'}),
        accessor: 'doi'
    }, {
        id: 'pmid',
        Header: intl.formatMessage({id: 'sheet.reference.pmid'}),
        accessor: 'pmid'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'citation_label':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'title':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'title_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'authors':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'authors_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'journal':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'journal_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'year':
                return <Value mbGoRefer="" labelType="year" value={cell.value}/>
            case 'volume':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'issue':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'pages':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'doi':
                return <Value mbGoRefer="" labelType="doi" value={cell.value}/>
            case 'pmid':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: referenceList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
