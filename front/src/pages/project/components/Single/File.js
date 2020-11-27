import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({file}) => {
    const shouldShowValue = useShouldShowValue()

    if (!file) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(file.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={file.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(file.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={file.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(file.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={file.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(file.local_folder_path, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.local_folder_path"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={file.local_folder_path}/></td>
                </tr>
            ) : null}
            {shouldShowValue(file.file_name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.file_name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={file.file_name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(file.file_format, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.file_format"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={file.file_format}/></td>
                </tr>
            ) : null}
            {shouldShowValue(file.download_url, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.download_url"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={file.download_url}/></td>
                </tr>
            ) : null}
            {shouldShowValue(file.md5, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file.md_5"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={file.md5}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
