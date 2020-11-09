import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepEnvironment}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepEnvironment) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepEnvironment.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_environment.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepEnvironment.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepEnvironment.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_environment.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepEnvironment.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepEnvironment.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_environment.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepEnvironment.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepEnvironment.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_environment.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepEnvironment.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepEnvironment.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_environment.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepEnvironment.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepEnvironment.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_environment.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepEnvironment.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
