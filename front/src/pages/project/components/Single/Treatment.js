import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({treatment}) => {
    const shouldShowValue = useShouldShowValue()

    if (!treatment) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(treatment.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.treatment.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={treatment.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(treatment.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.treatment.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={treatment.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(treatment.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.treatment.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={treatment.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(treatment.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.treatment.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={treatment.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(treatment.concentration, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.treatment.concentration"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={treatment.concentration}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
