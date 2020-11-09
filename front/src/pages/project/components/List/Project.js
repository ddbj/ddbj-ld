import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useProjectHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({projectList}) => {
    const hiddenColumns = useProjectHiddenColumns(projectList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.project.id'}),
        accessor: 'id'
    }, {
        id: 'title',
        Header: intl.formatMessage({id: 'sheet.project.title'}),
        accessor: 'title'
    }, {
        id: 'title_ja',
        Header: intl.formatMessage({id: 'sheet.project.title_ja'}),
        accessor: 'title_ja'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.project.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.project.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'person_creator',
        Header: intl.formatMessage({id: 'sheet.project.person_creator'}),
        accessor: 'person_creator'
    }, {
        id: 'person_contact',
        Header: intl.formatMessage({id: 'sheet.project.person_contact'}),
        accessor: 'person_contact'
    }, {
        id: 'person_principal_investigator',
        Header: intl.formatMessage({id: 'sheet.project.person_principal_investigator'}),
        accessor: 'person_principal_investigator'
    }, {
        id: 'person_submitter',
        Header: intl.formatMessage({id: 'sheet.project.person_submitter'}),
        accessor: 'person_submitter'
    }, {
        id: 'reference',
        Header: intl.formatMessage({id: 'sheet.project.reference'}),
        accessor: 'reference'
    }, {
        id: 'funding_source',
        Header: intl.formatMessage({id: 'sheet.project.funding_source'}),
        accessor: 'funding_source'
    }, {
        id: 'project_related',
        Header: intl.formatMessage({id: 'sheet.project.project_related'}),
        accessor: 'project_related'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.project.comment'}),
        accessor: 'comment'
    }, {
        id: 'comment_ja',
        Header: intl.formatMessage({id: 'sheet.project.comment_ja'}),
        accessor: 'comment_ja'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'title':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'title_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'description_ja':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'person_creator':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'person_contact':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'person_principal_investigator':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'person_submitter':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'reference':
                return <Value mbGoRefer="ref" labelType="string" value={cell.value}/>
            case 'funding_source':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'project_related':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'comment_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: projectList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
