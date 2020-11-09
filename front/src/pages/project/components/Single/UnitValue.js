import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({unitValue}) => {
    const shouldShowValue = useShouldShowValue()

    if (!unitValue) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(unitValue.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit_value.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={unitValue.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(unitValue.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit_value.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={unitValue.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(unitValue.type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit_value.type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={unitValue.type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(unitValue.value, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit_value.value"/></th>
                    <td><Value mbGoRefer="" labelType="float" value={unitValue.value}/></td>
                </tr>
            ) : null}
            {shouldShowValue(unitValue.unit, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit_value.unit"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={unitValue.unit}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
