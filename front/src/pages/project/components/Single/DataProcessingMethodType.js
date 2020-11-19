import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({dataProcessingMethodType}) => {
    const shouldShowValue = useShouldShowValue()

    if (!dataProcessingMethodType) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(dataProcessingMethodType.id, 'no') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method_type.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={dataProcessingMethodType.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethodType.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method_type.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataProcessingMethodType.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethodType.type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method_type.type"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={dataProcessingMethodType.type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethodType.ontology_uri, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method_type.ontology_uri"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataProcessingMethodType.ontology_uri}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethodType.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method_type.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataProcessingMethodType.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataProcessingMethodType.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_processing_method_type.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={dataProcessingMethodType.description}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
