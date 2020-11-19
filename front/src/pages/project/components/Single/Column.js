import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({column}) => {
    const shouldShowValue = useShouldShowValue()

    if (!column) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(column.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={column.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(column.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={column.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(column.type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={column.type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(column.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={column.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(column.product_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.product_name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={column.product_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(column.product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={column.product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(column.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={column.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(column.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.column.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={column.description}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
