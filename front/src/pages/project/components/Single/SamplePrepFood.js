import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepFood}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepFood) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepFood.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_food.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepFood.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepFood.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_food.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepFood.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepFood.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_food.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepFood.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepFood.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_food.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepFood.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepFood.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_food.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepFood.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepFood.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_food.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepFood.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
