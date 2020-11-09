import React, {useCallback, useMemo} from 'react'
import {usePagination, useTable} from 'react-table'
import {useIntl} from 'react-intl'

import {useSamplePlantHiddenColumns} from '../../../../hooks/project/display'

import ListTable from './ListTable'
import {ListViewValue as Value} from '../Value'

const List = ({samplePlantList}) => {
    const hiddenColumns = useSamplePlantHiddenColumns(samplePlantList)
    const intl = useIntl()

    const columns = useMemo(() => ([{
        id: 'id',
        Header: intl.formatMessage({id: 'sheet.sample_plant.id'}),
        accessor: 'id'
    }, {
        id: 'name',
        Header: intl.formatMessage({id: 'sheet.sample_plant.name'}),
        accessor: 'name'
    }, {
        id: 'name_ja',
        Header: intl.formatMessage({id: 'sheet.sample_plant.name_ja'}),
        accessor: 'name_ja'
    }, {
        id: 'sample_preparation_id',
        Header: intl.formatMessage({id: 'sheet.sample_plant.sample_preparation_id'}),
        accessor: 'sample_preparation_id'
    }, {
        id: 'sample_preparation_name',
        Header: intl.formatMessage({id: 'sheet.sample_plant.sample_preparation_name'}),
        accessor: 'sample_preparation_name'
    }, {
        id: 'comment',
        Header: intl.formatMessage({id: 'sheet.sample_plant.comment'}),
        accessor: 'comment'
    }, {
        id: 'description',
        Header: intl.formatMessage({id: 'sheet.sample_plant.description'}),
        accessor: 'description'
    }, {
        id: 'description_ja',
        Header: intl.formatMessage({id: 'sheet.sample_plant.description_ja'}),
        accessor: 'description_ja'
    }, {
        id: 'supplier',
        Header: intl.formatMessage({id: 'sheet.sample_plant.supplier'}),
        accessor: 'supplier'
    }, {
        id: 'supplier_product_id',
        Header: intl.formatMessage({id: 'sheet.sample_plant.supplier_product_id'}),
        accessor: 'supplier_product_id'
    }, {
        id: 'amount_collected',
        Header: intl.formatMessage({id: 'sheet.sample_plant.amount_collected'}),
        accessor: 'amount_collected'
    }, {
        id: 'treatment',
        Header: intl.formatMessage({id: 'sheet.sample_plant.treatment'}),
        accessor: 'treatment'
    }, {
        id: 'bio_sample_id',
        Header: intl.formatMessage({id: 'sheet.sample_plant.bio_sample_id'}),
        accessor: 'bio_sample_id'
    }, {
        id: 'species_name',
        Header: intl.formatMessage({id: 'sheet.sample_plant.species_name'}),
        accessor: 'species_name'
    }, {
        id: 'species_name_ja',
        Header: intl.formatMessage({id: 'sheet.sample_plant.species_name_ja'}),
        accessor: 'species_name_ja'
    }, {
        id: 'taxonomy_ncbi',
        Header: intl.formatMessage({id: 'sheet.sample_plant.taxonomy_ncbi'}),
        accessor: 'taxonomy_ncbi'
    }, {
        id: 'cultivar',
        Header: intl.formatMessage({id: 'sheet.sample_plant.cultivar'}),
        accessor: 'cultivar'
    }, {
        id: 'cultivar_ja',
        Header: intl.formatMessage({id: 'sheet.sample_plant.cultivar_ja'}),
        accessor: 'cultivar_ja'
    }, {
        id: 'genotype',
        Header: intl.formatMessage({id: 'sheet.sample_plant.genotype'}),
        accessor: 'genotype'
    }, {
        id: 'developmental_stage',
        Header: intl.formatMessage({id: 'sheet.sample_plant.developmental_stage'}),
        accessor: 'developmental_stage'
    }, {
        id: 'biomaterial_organ',
        Header: intl.formatMessage({id: 'sheet.sample_plant.biomaterial_organ'}),
        accessor: 'biomaterial_organ'
    }, {
        id: 'biomaterial_tissue',
        Header: intl.formatMessage({id: 'sheet.sample_plant.biomaterial_tissue'}),
        accessor: 'biomaterial_tissue'
    }, {
        id: 'mutant',
        Header: intl.formatMessage({id: 'sheet.sample_plant.mutant'}),
        accessor: 'mutant'
    }, {
        id: 'transgenic_line',
        Header: intl.formatMessage({id: 'sheet.sample_plant.transgenic_line'}),
        accessor: 'transgenic_line'
    }, {
        id: 'related_gene',
        Header: intl.formatMessage({id: 'sheet.sample_plant.related_gene'}),
        accessor: 'related_gene'
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
            case 'bio_sample_id':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'species_name':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'species_name_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'taxonomy_ncbi':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'cultivar':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'cultivar_ja':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'genotype':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'developmental_stage':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'biomaterial_organ':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'biomaterial_tissue':
                return <Value mbGoRefer="link" labelType="string" value={cell.value}/>
            case 'mutant':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'transgenic_line':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            case 'related_gene':
                return <Value mbGoRefer="" labelType="string" value={cell.value}/>
            default:
                return <Value mbGoRefer="" labelType="" value={cell.value}/>
        }
    }, [])

    const instance = useTable({
        columns,
        data: samplePlantList,
        initialState: {
            hiddenColumns,
        }
    }, usePagination)

    return <ListTable {...instance} renderCell={renderCell}/>
}

export default List
