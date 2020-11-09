import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({reference}) => {
    const shouldShowValue = useShouldShowValue()

    if (!reference) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(reference.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={reference.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.citation_label, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.citation_label"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.citation_label}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.title, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.title"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.title}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.title_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.title_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.title_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.authors, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.authors"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.authors}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.authors_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.authors_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.authors_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.journal, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.journal"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.journal}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.journal_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.journal_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.journal_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.year, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.year"/></th>
                    <td><Value mbGoRefer="" labelType="year" value={reference.year}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.volume, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.volume"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.volume}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.issue, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.issue"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.issue}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.pages, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.pages"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.pages}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.doi, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.doi"/></th>
                    <td><Value mbGoRefer="" labelType="doi" value={reference.doi}/></td>
                </tr>
            ) : null}
            {shouldShowValue(reference.pmid, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.reference.pmid"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={reference.pmid}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
