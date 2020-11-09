import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurementMethodNmr}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurementMethodNmr) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurementMethodNmr.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurementMethodNmr.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={measurementMethodNmr.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodNmr.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodNmr.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.nmr_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.nmr_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodNmr.nmr_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.nmr_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.nmr_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodNmr.nmr_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.other_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.other_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodNmr.other_condition_id}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.other_condition_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.other_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodNmr.other_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodNmr.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_nmr.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={measurementMethodNmr.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
