import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({term}) => {
    const shouldShowValue = useShouldShowValue()

    if (!term) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(term.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.term.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={term.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(term.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.term.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={term.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(term.type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.term.type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={term.type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(term.ontology_uri, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.term.ontology_uri"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={term.ontology_uri}/></td>
                </tr>
            ) : null}
            {shouldShowValue(term.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.term.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={term.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(term.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.term.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={term.description}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
