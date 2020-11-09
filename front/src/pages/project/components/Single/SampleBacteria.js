import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleBacteria}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleBacteria) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleBacteria.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleBacteria.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleBacteria.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleBacteria.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleBacteria.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleBacteria.sample_preparation_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleBacteria.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleBacteria.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleBacteria.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleBacteria.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleBacteria.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleBacteria.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleBacteria.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_bacteria.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleBacteria.treatment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
