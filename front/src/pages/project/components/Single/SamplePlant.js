import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePlant}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePlant) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePlant.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePlant.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePlant.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={samplePlant.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={samplePlant.sample_preparation_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePlant.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePlant.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePlant.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={samplePlant.treatment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.bio_sample_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.bio_sample_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.bio_sample_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.species_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.species_name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.species_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.species_name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.species_name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.species_name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.taxonomy_ncbi, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.taxonomy_ncbi"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.taxonomy_ncbi}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.cultivar, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.cultivar"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.cultivar}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.cultivar_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.cultivar_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.cultivar_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.genotype, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.genotype"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.genotype}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.developmental_stage, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.developmental_stage"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.developmental_stage}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.biomaterial_organ, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.biomaterial_organ"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={samplePlant.biomaterial_organ}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.biomaterial_tissue, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.biomaterial_tissue"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={samplePlant.biomaterial_tissue}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.mutant, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.mutant"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.mutant}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.transgenic_line, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.transgenic_line"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.transgenic_line}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePlant.related_gene, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_plant.related_gene"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePlant.related_gene}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
