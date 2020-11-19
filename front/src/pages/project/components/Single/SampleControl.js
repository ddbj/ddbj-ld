import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleControl}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleControl) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleControl.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleControl.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleControl.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleControl.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleControl.sample_preparation_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleControl.sample_preparation_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleControl.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleControl.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleControl.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleControl.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleControl.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleControl.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleControl.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_control.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleControl.treatment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
