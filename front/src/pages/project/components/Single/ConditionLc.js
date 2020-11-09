import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({conditionLc}) => {
    const shouldShowValue = useShouldShowValue()

    if (!conditionLc) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(conditionLc.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={conditionLc.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.instrument, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.instrument"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionLc.instrument}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.column, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.column"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionLc.column}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.column_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.column_type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionLc.column_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.column_other, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.column_other"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.column_other}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.column_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.column_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionLc.column_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.column_pressure, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.column_pressure"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionLc.column_pressure}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.solvent_a, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.solvent_a"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.solvent_a}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.solvent_b, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.solvent_b"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.solvent_b}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.solvent_other, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.solvent_other"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.solvent_other}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.flow_gradient, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.flow_gradient"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.flow_gradient}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.flow_rate, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.flow_rate"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionLc.flow_rate}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.elution_detector, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.elution_detector"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionLc.elution_detector}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.elution_detector_wave_length_min, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.elution_detector_wave_length_min"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionLc.elution_detector_wave_length_min}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.elution_detector_wave_length_max, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.elution_detector_wave_length_max"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionLc.elution_detector_wave_length_max}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.analytical_time, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.analytical_time"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionLc.analytical_time}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionLc.control_software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_lc.control_software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionLc.control_software}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
