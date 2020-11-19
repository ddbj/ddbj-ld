import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({dataPreprocessing}) => {
    const shouldShowValue = useShouldShowValue()

    if (!dataPreprocessing) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(dataPreprocessing.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={dataPreprocessing.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataPreprocessing.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={dataPreprocessing.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={dataPreprocessing.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.general, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.general"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.general}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.peak_detection, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.peak_detection"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.peak_detection}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.peak_alignment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.peak_alignment"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.peak_alignment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.spectral_extraction, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.spectral_extraction"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.spectral_extraction}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.retention_time_correction, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.retention_time_correction"/></th>
                    <td><Value mbGoRefer="inner" labelType="string"
                               value={dataPreprocessing.retention_time_correction}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.summarisation, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.summarisation"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.summarisation}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.normalisation, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.normalisation"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.normalisation}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.data_transformation, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.data_transformation"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.data_transformation}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.scaling, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.scaling"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={dataPreprocessing.scaling}/></td>
                </tr>
            ) : null}
            {shouldShowValue(dataPreprocessing.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.data_preprocessing.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={dataPreprocessing.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
