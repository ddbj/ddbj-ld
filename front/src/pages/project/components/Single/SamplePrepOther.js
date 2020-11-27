import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepOther}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepOther) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepOther.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_other.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepOther.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepOther.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_other.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepOther.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepOther.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_other.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepOther.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepOther.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_other.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepOther.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepOther.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_other.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepOther.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepOther.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_other.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepOther.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
