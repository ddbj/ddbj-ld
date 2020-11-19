import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({extractionMethod}) => {
    const shouldShowValue = useShouldShowValue()

    if (!extractionMethod) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(extractionMethod.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={extractionMethod.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={extractionMethod.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={extractionMethod.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={extractionMethod.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={extractionMethod.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.internal_standard, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.internal_standard"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={extractionMethod.internal_standard}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.derivatisation, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.derivatisation"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={extractionMethod.derivatisation}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.extract_concentration, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.extract_concentration"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={extractionMethod.extract_concentration}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.extract_storage_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.extract_storage_method"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={extractionMethod.extract_storage_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(extractionMethod.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.extraction_method.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={extractionMethod.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
