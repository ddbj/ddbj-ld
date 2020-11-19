import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({annotationMethod}) => {
    const shouldShowValue = useShouldShowValue()

    if (!annotationMethod) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(annotationMethod.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={annotationMethod.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.name"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={annotationMethod.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={annotationMethod.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={annotationMethod.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.software, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.software"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={annotationMethod.software}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.parameters, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.parameters"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={annotationMethod.parameters}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.spectral_library, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.spectral_library"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={annotationMethod.spectral_library}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.evidence, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.evidence"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={annotationMethod.evidence}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.annotation_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.annotation_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={annotationMethod.annotation_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(annotationMethod.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.annotation_method.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={annotationMethod.reference}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
