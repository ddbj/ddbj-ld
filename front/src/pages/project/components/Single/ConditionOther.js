import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({conditionOther}) => {
    const shouldShowValue = useShouldShowValue()

    if (!conditionOther) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(conditionOther.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_other.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={conditionOther.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionOther.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_other.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionOther.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionOther.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_other.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionOther.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionOther.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_other.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={conditionOther.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionOther.instrument, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_other.instrument"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionOther.instrument}/></td>
                </tr>
            ) : null}
            {shouldShowValue(conditionOther.control_software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.condition_other.control_software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={conditionOther.control_software}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
