import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurementMethodCems}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurementMethodCems) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurementMethodCems.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurementMethodCems.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={measurementMethodCems.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodCems.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodCems.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.ce_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.ce_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodCems.ce_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.ce_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.ce_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodCems.ce_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.ms_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.ms_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodCems.ms_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.ms_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.ms_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodCems.ms_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.other_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.other_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodCems.other_condition_id}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.other_condition_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.other_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodCems.other_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodCems.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_cems.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={measurementMethodCems.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
