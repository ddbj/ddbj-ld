import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useSampleClinicalHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({sampleClinicalList}) => {
    const hiddenColumns = useSampleClinicalHiddenColumns(sampleClinicalList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.name'}),
        accessor: 'name'
    }, {
        id: 'name_ja',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.name_ja'}),
        accessor: 'name_ja'
    }, {
        id: 'sample_preparation_id',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.sample_preparation_id'}),
        accessor: 'sample_preparation_id'
    }, {
        id: 'sample_preparation_name',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.sample_preparation_name'}),
        accessor: 'sample_preparation_name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'supplier',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.supplier'}),
        accessor: 'supplier'
    }, {
        id: 'supplier_product_id',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.supplier_product_id'}),
        accessor: 'supplier_product_id'
    }, {
        id: 'amount_collected',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.amount_collected'}),
        accessor: 'amount_collected'
    }, {
        id: 'treatment',
        Header: intl.formatMessage({id: 'sheet.sample_clinical.treatment'}),
        accessor: 'treatment'
    }]), [intl])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'id':
                return <Value mbGoRefer="inner" labelType="id" value={cell.value}/>
            case 'name':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'name_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'sample_preparation_id':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'sample_preparation_name':
                return <Value mbGoRefer="inner" labelType="string" value={cell.value}/>
            case 'comment':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'description':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'description_ja':
                return <Value mbGoRefer="" labelType="text" value={cell.value}/>
            case 'supplier':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'supplier_product_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'amount_collected':
                return <Value mbGoRefer="no" labelType="string" value={cell.value}/>
            case 'treatment':
                return <Value mbGoRefer="inner" labelType="text" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: sampleClinicalList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
