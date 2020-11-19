import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({software}) => {
    const shouldShowValue = useShouldShowValue()

    if (!software) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(software.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.software.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={software.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(software.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.software.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={software.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(software.version, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.software.version"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={software.version}/></td>
                </tr>
            ) : null}
            {shouldShowValue(software.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.software.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={software.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(software.product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.software.product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={software.product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(software.available_url, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.software.available_url"/></th>
                    <td><Value mbGoRefer="" labelType="url" value={software.available_url}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
