import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrep}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrep) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrep.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrep.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrep.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrep.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrep.category, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep.category"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrep.category}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrep.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrep.comment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
