import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({analyzedRawFile}) => {
    const shouldShowValue = useShouldShowValue()

    if (!analyzedRawFile) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(analyzedRawFile.id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_raw_file.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={analyzedRawFile.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedRawFile.data_analysis_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_raw_file.data_analysis_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={analyzedRawFile.data_analysis_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedRawFile.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_raw_file.name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={analyzedRawFile.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedRawFile.project_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_raw_file.project_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={analyzedRawFile.project_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(analyzedRawFile.category, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.analyzed_raw_file.category"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={analyzedRawFile.category}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
