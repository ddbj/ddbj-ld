import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useExperimentalDesignHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({experimentalDesignList}) => {
    const hiddenColumns = useExperimentalDesignHiddenColumns(experimentalDesignList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.experimental_design.id'}),
        accessor: 'id'
    }, {
        id: 'experiment_type',
        Header: intl.formatMessage({id: 'sheet.experimental_design.experiment_type'}),
        accessor: 'experiment_type'
    }, {
        id: 'experimental_factor',
        Header: intl.formatMessage({id: 'sheet.experimental_design.experimental_factor'}),
        accessor: 'experimental_factor'
    }, {
        id: 'quality_control',
        Header: intl.formatMessage({id: 'sheet.experimental_design.quality_control'}),
        accessor: 'quality_control'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.experimental_design.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.experimental_design.description'}),
        accessor: 'description'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'experiment_type':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'experimental_factor':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'quality_control':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
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
        data: experimentalDesignList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
