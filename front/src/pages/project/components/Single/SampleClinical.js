import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleClinical}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleClinical) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleClinical.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleClinical.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleClinical.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleClinical.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleClinical.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleClinical.sample_preparation_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleClinical.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleClinical.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleClinical.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleClinical.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleClinical.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleClinical.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleClinical.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_clinical.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleClinical.treatment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
