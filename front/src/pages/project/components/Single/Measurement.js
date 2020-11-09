import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurement}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurement) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurement.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurement.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.experiment_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.experiment_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.experiment_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.experiment_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.experiment_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.experiment_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.sample_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.sample_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.sample_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.sample_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.sample_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.sample_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.extract_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.extract_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurement.extract_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.extraction_method_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.extraction_method_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.extraction_method_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.extraction_method_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.extraction_method_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.extraction_method_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.measurement_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.measurement_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurement.measurement_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.measurement_method_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.measurement_method_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.measurement_method_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.measurement_method_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.measurement_method_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.measurement_method_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.date_time, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.date_time"/></th>
                    <td><Value mbGoRefer="" labelType="date" value={measurement.date_time}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.person_experimenter, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.person_experimenter"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={measurement.person_experimenter}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.comment, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurement.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurement.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={measurement.description}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
