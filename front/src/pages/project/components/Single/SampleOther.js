import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleOther}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleOther) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleOther.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleOther.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleOther.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleOther.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleOther.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleOther.sample_preparation_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleOther.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleOther.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleOther.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleOther.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleOther.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleOther.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleOther.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_other.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleOther.treatment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
