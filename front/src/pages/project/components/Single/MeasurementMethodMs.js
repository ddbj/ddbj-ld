import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurementMethodMs}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurementMethodMs) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurementMethodMs.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurementMethodMs.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={measurementMethodMs.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodMs.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodMs.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.ms_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.ms_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodMs.ms_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.ms_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.ms_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodMs.ms_condition_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.other_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.other_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodMs.other_condition_id}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.other_condition_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.other_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodMs.other_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodMs.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_ms.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={measurementMethodMs.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
