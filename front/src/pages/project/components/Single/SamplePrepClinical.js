import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepClinical}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepClinical) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepClinical.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_clinical.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepClinical.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepClinical.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_clinical.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepClinical.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepClinical.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_clinical.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepClinical.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepClinical.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_clinical.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepClinical.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepClinical.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_clinical.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepClinical.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepClinical.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_clinical.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepClinical.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
