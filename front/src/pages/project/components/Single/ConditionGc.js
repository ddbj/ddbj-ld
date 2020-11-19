import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({conditionGc}) => {
    const shouldShowValue = useShouldShowValue()

    if (!conditionGc) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(conditionGc.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={conditionGc.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionGc.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionGc.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionGc.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.instrument, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.instrument"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionGc.instrument}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.column, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.column"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionGc.column}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.column_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.column_type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionGc.column_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.column_other, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.column_other"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionGc.column_other}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.column_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.column_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionGc.column_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.column_pressure, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.column_pressure"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionGc.column_pressure}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.temperature_gradient, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.temperature_gradient"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionGc.temperature_gradient}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.flow_gradient, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.flow_gradient"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionGc.flow_gradient}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.flow_rate, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.flow_rate"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionGc.flow_rate}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.flow_gas, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.flow_gas"/></th>
                    <td><Value mbGoRefer="" labelType="" value={conditionGc.flow_gas}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.analytical_time, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.analytical_time"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionGc.analytical_time}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionGc.control_software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_gc.control_software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionGc.control_software}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
