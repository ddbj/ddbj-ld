import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({conditionMs}) => {
    const shouldShowValue = useShouldShowValue()

    if (!conditionMs) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(conditionMs.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={conditionMs.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.instrument, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.instrument"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionMs.instrument}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.ms_instrument_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.ms_instrument_type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.ms_instrument_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.ion_source, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.ion_source"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.ion_source}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.ionization_polarity, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.ionization_polarity"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.ionization_polarity}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.ionization_energy, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.ionization_energy"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.ionization_energy}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.scan_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.scan_type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.scan_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.full_scan_mz_range, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.full_scan_mz_range"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.full_scan_mz_range}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.ms_acquisition_rate, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.ms_acquisition_rate"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.ms_acquisition_rate}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.msn_acquisition_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.msn_acquisition_method"/></th>
                    <td><Value mbGoRefer="no" labelType="text" value={conditionMs.msn_acquisition_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.scan_program_details, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.scan_program_details"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={conditionMs.scan_program_details}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.resolution, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.resolution"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.resolution}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.mz_accuracy_full_scan, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.mz_accuracy_full_scan"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.mz_accuracy_full_scan}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.mz_accuracy_msn_scan, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.mz_accuracy_msn_scan"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.mz_accuracy_msn_scan}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.capillary_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.capillary_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.capillary_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.collision_energy, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.collision_energy"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.collision_energy}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.ion_source_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.ion_source_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.ion_source_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.ion_spray_voltage, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.ion_spray_voltage"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.ion_spray_voltage}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.fragmentation_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.fragmentation_method"/></th>
                    <td><Value mbGoRefer="no" labelType="text" value={conditionMs.fragmentation_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.retention_index_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.retention_index_method"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={conditionMs.retention_index_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.desolvation_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.desolvation_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={conditionMs.desolvation_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.sheath_gas, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.sheath_gas"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionMs.sheath_gas}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionMs.control_software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_ms.control_software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionMs.control_software}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
