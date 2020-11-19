import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({lightCondition}) => {
    const shouldShowValue = useShouldShowValue()

    if (!lightCondition) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(lightCondition.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.light_condition.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={lightCondition.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(lightCondition.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.light_condition.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={lightCondition.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(lightCondition.light_quality, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.light_condition.light_quality"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={lightCondition.light_quality}/></td>
                </tr>
            ) : null}
            {shouldShowValue(lightCondition.light_intensity, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.light_condition.light_intensity"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={lightCondition.light_intensity}/></td>
                </tr>
            ) : null}
            {shouldShowValue(lightCondition.peak_wave_length, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.light_condition.peak_wave_length"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={lightCondition.peak_wave_length}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
