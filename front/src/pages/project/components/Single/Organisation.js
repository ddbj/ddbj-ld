import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({organisation}) => {
    const shouldShowValue = useShouldShowValue()

    if (!organisation) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(organisation.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={organisation.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(organisation.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={organisation.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(organisation.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={organisation.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(organisation.abbreviation, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.abbreviation"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={organisation.abbreviation}/></td>
                </tr>
            ) : null}
            {shouldShowValue(organisation.homepage, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.homepage"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={organisation.homepage}/></td>
                </tr>
            ) : null}
            {shouldShowValue(organisation.address, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.address"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={organisation.address}/></td>
                </tr>
            ) : null}
            {shouldShowValue(organisation.address_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.address_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={organisation.address_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(organisation.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.organisation.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={organisation.description}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
