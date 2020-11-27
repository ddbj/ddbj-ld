import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({rawDataFile}) => {
    const shouldShowValue = useShouldShowValue()

    if (!rawDataFile) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(rawDataFile.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={rawDataFile.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.measurement_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.measurement_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={rawDataFile.measurement_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.data_type_id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.data_type_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={rawDataFile.data_type_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.data_type, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.data_type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={rawDataFile.data_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={rawDataFile.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={rawDataFile.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={rawDataFile.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.local_folder_path, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.local_folder_path"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={rawDataFile.local_folder_path}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.file_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.file_name_raw"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={rawDataFile.file_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.file_format, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_file.file_format_raw"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={rawDataFile.file_format}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.download_url, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_download_file.download_url"/></th>
                    <td><Value mbGoRefer="link" labelType="uri" value={rawDataFile.download_url}/></td>
                </tr>
            ) : null}
            {shouldShowValue(rawDataFile.md5, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.raw_data_download_file.md5"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={rawDataFile.md5}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
