import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleAnimal}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleAnimal) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleAnimal.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleAnimal.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleAnimal.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleAnimal.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleAnimal.sample_preparation_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleAnimal.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleAnimal.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleAnimal.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleAnimal.treatment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.bio_sample_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.bio_sample_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.bio_sample_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.species_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.species_name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.species_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.species_name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.species_name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.species_name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.taxonomy_ncbi, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.taxonomy_ncbi"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.taxonomy_ncbi}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.cultivar, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.cultivar"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.cultivar}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.cultivar_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.cultivar_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.cultivar_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.genotype, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.genotype"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.genotype}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.biomaterial_organ, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.biomaterial_organ"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={sampleAnimal.biomaterial_organ}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.biomaterial_tissue, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.biomaterial_tissue"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={sampleAnimal.biomaterial_tissue}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.mutant, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.mutant"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.mutant}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.transgenic_line, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.transgenic_line"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.transgenic_line}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.related_gene, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.related_gene"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.related_gene}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.biomaterial_line, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.biomaterial_line"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={sampleAnimal.biomaterial_line}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.phenotype, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.phenotype"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={sampleAnimal.phenotype}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.phenotypic_sex, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.phenotypic_sex"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={sampleAnimal.phenotypic_sex}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.disease, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.disease"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.disease}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleAnimal.clinical_signs, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_animal.clinical_signs"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleAnimal.clinical_signs}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
