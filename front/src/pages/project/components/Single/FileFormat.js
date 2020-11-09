import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({fileFormat}) => {
    const shouldShowValue = useShouldShowValue()

    if (!fileFormat) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(fileFormat.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file_format.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={fileFormat.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(fileFormat.name, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file_format.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={fileFormat.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(fileFormat.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.file_format.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={fileFormat.comment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
