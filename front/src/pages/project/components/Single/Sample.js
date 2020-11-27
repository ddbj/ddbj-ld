import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sample}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sample) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sample.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sample.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sample.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sample.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sample.category, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample.category"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sample.category}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sample.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sample.comment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
