import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({analyzedResultFile}) => {
    const shouldShowValue = useShouldShowValue()

    if (!analyzedResultFile) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(analyzedResultFile.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={analyzedResultFile.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.data_analysis_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.data_analysis_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={analyzedResultFile.data_analysis_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.data_analysis_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.data_analysis_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={analyzedResultFile.data_analysis_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.data_type_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.data_type_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={analyzedResultFile.data_type_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.data_type, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.data_type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={analyzedResultFile.data_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={analyzedResultFile.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.description"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={analyzedResultFile.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.local_folder_path, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.local_folder_path"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={analyzedResultFile.local_folder_path}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.file_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.file_name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={analyzedResultFile.file_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.file_format, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.file_format"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={analyzedResultFile.file_format}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.download_url, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.download_url"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={analyzedResultFile.download_url}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedResultFile.md5, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_result_file.md5"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={analyzedResultFile.md5}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
