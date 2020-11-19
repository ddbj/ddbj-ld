import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({instrument}) => {
    const shouldShowValue = useShouldShowValue()

    if (!instrument) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(instrument.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={instrument.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(instrument.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={instrument.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(instrument.type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={instrument.type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(instrument.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={instrument.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(instrument.product_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.product_name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={instrument.product_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(instrument.product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={instrument.product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(instrument.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={instrument.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(instrument.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.instrument.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={instrument.description}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
