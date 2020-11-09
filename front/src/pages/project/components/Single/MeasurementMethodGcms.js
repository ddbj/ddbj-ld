import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurementMethodGcms}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurementMethodGcms) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurementMethodGcms.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurementMethodGcms.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={measurementMethodGcms.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodGcms.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethodGcms.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.gc_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.gc_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodGcms.gc_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.gc_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.gc_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodGcms.gc_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.ms_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.ms_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodGcms.ms_condition_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.ms_condition_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.ms_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodGcms.ms_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.other_condition_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.other_condition_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodGcms.other_condition_id}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.other_condition_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.other_condition_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurementMethodGcms.other_condition_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethodGcms.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method_gcms.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={measurementMethodGcms.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
