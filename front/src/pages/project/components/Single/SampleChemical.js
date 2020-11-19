import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleChemical}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleChemical) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleChemical.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleChemical.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleChemical.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleChemical.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleChemical.sample_preparation_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleChemical.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleChemical.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleChemical.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleChemical.treatment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.chemical_formula, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.chemical_formula"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.chemical_formula}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.smiles, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.smiles"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.smiles}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.inchi, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.inchi"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.inchi}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleChemical.inchi_key, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_chemical.inchi_key"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleChemical.inchi_key}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
