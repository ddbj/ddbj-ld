import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({person}) => {
    const shouldShowValue = useShouldShowValue()

    if (!person) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(person.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={person.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.label, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.label"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={person.label}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.label_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.label_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={person.label_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.name_family, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.name_family"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={person.name_family}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.name_first, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.name_first"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={person.name_first}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.name_family_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.name_family_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={person.name_family_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.name_first_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.name_first_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={person.name_first_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.name_middle, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.name_middle"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={person.name_middle}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.organisation, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.organisation"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={person.organisation}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.email, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.email"/></th>
                    <td><Value mbGoRefer="" labelType="email" value={person.email}/></td>
                </tr>
            ) : null}
            {shouldShowValue(person.orcid, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.person.orcid"/></th>
                    <td><Value mbGoRefer="" labelType="orcid" value={person.orcid}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
