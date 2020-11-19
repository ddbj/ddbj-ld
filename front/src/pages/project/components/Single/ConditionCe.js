import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({conditionCe}) => {
    const shouldShowValue = useShouldShowValue()

    if (!conditionCe) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(conditionCe.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={conditionCe.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionCe.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionCe.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionCe.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.instrument, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.instrument"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionCe.instrument}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.column, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.column"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionCe.column}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.column_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.column_type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionCe.column_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.column_other, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.column_other"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionCe.column_other}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.column_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.column_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionCe.column_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.column_pressure, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.column_pressure"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionCe.column_pressure}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.solvent, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.solvent"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionCe.solvent}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.flow_gradient, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.flow_gradient"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionCe.flow_gradient}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.flow_rate, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.flow_rate"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionCe.flow_rate}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.elution_detector, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.elution_detector"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionCe.elution_detector}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.elution_detector_wave_length_min, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.elution_detector_wave_length_min"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionCe.elution_detector_wave_length_min}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.elution_detector_wave_length_max, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.elution_detector_wave_length_max"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionCe.elution_detector_wave_length_max}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.analytical_time, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.analytical_time"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionCe.analytical_time}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionCe.control_software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ce.control_software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionCe.control_software}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
