import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({experiment}) => {
    const shouldShowValue = useShouldShowValue()

    if (!experiment) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(experiment.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={experiment.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={experiment.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={experiment.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={experiment.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.measurement_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.measurement_type"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={experiment.measurement_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.technology_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.technology_type"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={experiment.technology_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.technology_platform, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.technology_platform"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={experiment.technology_platform}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.date_time, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.date_time"/></th>
                    <td><Value mbGoRefer="" labelType="date" value={experiment.date_time}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.person_experimenter, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.person_experimenter"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={experiment.person_experimenter}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.experimental_design, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.experimental_design"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={experiment.experimental_design}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.data_analysis_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.data_analysis_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={experiment.data_analysis_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experiment.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.experiment.reference"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={experiment.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
