import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({experimentalDesign}) => {
    const shouldShowValue = useShouldShowValue()

    if (!experimentalDesign) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(experimentalDesign.id, 'yes') ? (
                <tr>
                    <th style={{width: 200}}><FormattedMessage id="sheet.experimental_design.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={experimentalDesign.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experimentalDesign.experiment_type, 'yes') ? (
                <tr>
                    <th style={{width: 200}}><FormattedMessage id="sheet.experimental_design.experiment_type"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={experimentalDesign.experiment_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experimentalDesign.experimental_factor, 'yes') ? (
                <tr>
                    <th style={{width: 200}}><FormattedMessage id="sheet.experimental_design.experimental_factor"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={experimentalDesign.experimental_factor}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experimentalDesign.quality_control, '') ? (
                <tr>
                    <th style={{width: 200}}><FormattedMessage id="sheet.experimental_design.quality_control"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={experimentalDesign.quality_control}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experimentalDesign.comment, '') ? (
                <tr>
                    <th style={{width: 200}}><FormattedMessage id="sheet.experimental_design.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={experimentalDesign.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(experimentalDesign.description, '') ? (
                <tr>
                    <th style={{width: 200}}><FormattedMessage id="sheet.experimental_design.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={experimentalDesign.description}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
