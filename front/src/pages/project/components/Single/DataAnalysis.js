import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({dataAnalysis}) => {
    const shouldShowValue = useShouldShowValue()

    if (!dataAnalysis) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(dataAnalysis.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={dataAnalysis.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataAnalysis.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataAnalysis.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={dataAnalysis.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.data_preprocessing, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.data_preprocessing"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataAnalysis.data_preprocessing}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.statictical_analysis, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.statictical_analysis"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataAnalysis.statictical_analysis}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.univariate_analysis, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.univariate_analysis"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataAnalysis.univariate_analysis}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.multivariate_analysis, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.multivariate_analysis"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataAnalysis.multivariate_analysis}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.visualisation, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.visualisation"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataAnalysis.visualisation}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.annotation_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.annotation_method"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataAnalysis.annotation_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataAnalysis.recommended_decimal_place_mz, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_analysis.recommended_decimal_place_mz"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataAnalysis.recommended_decimal_place_mz}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
