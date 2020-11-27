import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({measurementMethod}) => {
    const shouldShowValue = useShouldShowValue()

    if (!measurementMethod) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(measurementMethod.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={measurementMethod.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethod.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethod.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethod.category, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method.category"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethod.category}/></td>
                </tr>
            ) : null}
            {shouldShowValue(measurementMethod.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.measurement_method.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={measurementMethod.comment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
