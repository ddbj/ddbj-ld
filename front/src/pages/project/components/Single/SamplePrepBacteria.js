import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepBacteria}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepBacteria) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepBacteria.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_bacteria.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepBacteria.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepBacteria.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_bacteria.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepBacteria.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepBacteria.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_bacteria.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepBacteria.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepBacteria.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_bacteria.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepBacteria.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepBacteria.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_bacteria.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepBacteria.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepBacteria.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_bacteria.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepBacteria.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
