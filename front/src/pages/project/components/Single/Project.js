import React from 'react'
import { FormattedMessage } from 'react-intl'

import { useShouldShowValue } from '../../../../hooks/project/display'

import Table from './SingleTable'
import { ListViewValue as Value} from '../Value'

const Single = ({ project }) => {
    const shouldShowValue = useShouldShowValue()

    if (!project) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(project.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.id" /></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={project.id} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.title, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.title" /></th>
                    <td><Value mbGoRefer="" labelType="string" value={project.title} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.title_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.title_ja" /></th>
                    <td><Value mbGoRefer="" labelType="string" value={project.title_ja} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.description" /></th>
                    <td><Value mbGoRefer="" labelType="text" value={project.description} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.description_ja" /></th>
                    <td><Value mbGoRefer="" labelType="text" value={project.description_ja} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.person_creator, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.person_creator" /></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={project.person_creator} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.person_contact, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.person_contact" /></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={project.person_contact} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.person_principal_investigator, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.person_principal_investigator" /></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={project.person_principal_investigator} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.person_submitter, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.person_submitter" /></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={project.person_submitter} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.reference" /></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={project.reference} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.funding_source, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.funding_source" /></th>
                    <td><Value mbGoRefer="" labelType="string" value={project.funding_source} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.project_related, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.project_related" /></th>
                    <td><Value mbGoRefer="yes" labelType="url" value={project.project_related} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.comment" /></th>
                    <td><Value mbGoRefer="" labelType="string" value={project.comment} /></td>
                </tr>
            ) : null}
            {shouldShowValue(project.comment_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.project.comment_ja" /></th>
                    <td><Value mbGoRefer="" labelType="string" value={project.comment_ja} /></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
