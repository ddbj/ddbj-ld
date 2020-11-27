import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurementMethodLcms}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurementMethodLcms) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurementMethodLcms.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurementMethodLcms.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={measurementMethodLcms.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodLcms.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodLcms.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.lc_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.lc_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodLcms.lc_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.lc_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.lc_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodLcms.lc_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.ms_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.ms_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodLcms.ms_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.ms_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.ms_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodLcms.ms_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.other_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.other_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodLcms.other_condition_id}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.other_condition_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.other_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodLcms.other_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodLcms.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_lcms.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={measurementMethodLcms.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
