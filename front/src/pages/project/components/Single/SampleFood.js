import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleFood}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleFood) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleFood.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleFood.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleFood.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleFood.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleFood.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleFood.sample_preparation_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleFood.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleFood.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleFood.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleFood.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleFood.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleFood.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleFood.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_food.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleFood.treatment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
