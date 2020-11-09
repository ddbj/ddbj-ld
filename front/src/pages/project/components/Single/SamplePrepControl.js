import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepControl}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepControl) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepControl.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_control.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepControl.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepControl.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_control.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepControl.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepControl.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_control.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepControl.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepControl.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_control.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepControl.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepControl.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_control.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepControl.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepControl.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_control.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepControl.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
