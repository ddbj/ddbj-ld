import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({conditionNmr}) => {
    const shouldShowValue = useShouldShowValue()

    if (!conditionNmr) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(conditionNmr.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_nmr.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={conditionNmr.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionNmr.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_nmr.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionNmr.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionNmr.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_nmr.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionNmr.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionNmr.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_nmr.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionNmr.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionNmr.instrument, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_nmr.instrument"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionNmr.instrument}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionNmr.control_software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_nmr.control_software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionNmr.control_software}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
