import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({unit}) => {
    const shouldShowValue = useShouldShowValue()

    if (!unit) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(unit.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={unit.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(unit.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={unit.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(unit.super_class, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.unit.super_class"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={unit.super_class}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
