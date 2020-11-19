import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepChemical}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepChemical) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepChemical.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_chemical.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepChemical.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepChemical.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_chemical.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepChemical.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepChemical.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_chemical.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepChemical.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepChemical.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_chemical.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepChemical.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepChemical.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_chemical.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepChemical.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepChemical.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_chemical.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepChemical.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
