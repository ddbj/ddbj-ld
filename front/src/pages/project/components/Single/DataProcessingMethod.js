import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({dataProcessingMethod}) => {
    const shouldShowValue = useShouldShowValue()

    if (!dataProcessingMethod) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(dataProcessingMethod.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={dataProcessingMethod.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataProcessingMethod.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={dataProcessingMethod.type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.method_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.method_type"/></th>
                    <td><Value mbGoRefer="link" labelType="string" value={dataProcessingMethod.method_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataProcessingMethod.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={dataProcessingMethod.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataProcessingMethod.software}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.parameter, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.parameter"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={dataProcessingMethod.parameter}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethod.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={dataProcessingMethod.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
