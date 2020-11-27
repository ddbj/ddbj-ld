import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({supplementaryFile}) => {
    const shouldShowValue = useShouldShowValue()

    if (!supplementaryFile) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(supplementaryFile.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={supplementaryFile.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(supplementaryFile.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={supplementaryFile.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(supplementaryFile.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={supplementaryFile.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(supplementaryFile.local_folder_path, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.local_folder_path"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={supplementaryFile.local_folder_path}/></td>
                </tr>
            ) : null}
            {shouldShowValue(supplementaryFile.file_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.file_name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={supplementaryFile.file_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(supplementaryFile.file_format, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.file_format"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={supplementaryFile.file_format}/></td>
                </tr>
            ) : null}
            {shouldShowValue(supplementaryFile.download_url, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.download_url"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={supplementaryFile.download_url}/></td>
                </tr>
            ) : null}
            {shouldShowValue(supplementaryFile.md5, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.supplementary_file.md_5"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={supplementaryFile.md5}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
