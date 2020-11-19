import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurementMethodOther}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurementMethodOther) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurementMethodOther.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_other.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurementMethodOther.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodOther.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_other.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={measurementMethodOther.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodOther.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_other.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodOther.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodOther.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_other.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodOther.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodOther.other_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_other.other_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodOther.other_condition_id}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodOther.other_condition_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_other.other_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string"
                               value={measurementMethodOther.other_condition_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodOther.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_other.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={measurementMethodOther.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
