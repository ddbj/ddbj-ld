import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({sampleEnvironment}) => {
    const shouldShowValue = useShouldShowValue()

    if (!sampleEnvironment) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(sampleEnvironment.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={sampleEnvironment.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleEnvironment.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleEnvironment.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.sample_preparation_id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.sample_preparation_id"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleEnvironment.sample_preparation_id}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.sample_preparation_name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.sample_preparation_name"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={sampleEnvironment.sample_preparation_name}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.comment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.comment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleEnvironment.comment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.description, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleEnvironment.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={sampleEnvironment.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.supplier, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.supplier"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleEnvironment.supplier}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.supplier_product_id, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.supplier_product_id"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={sampleEnvironment.supplier_product_id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.amount_collected, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.amount_collected"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={sampleEnvironment.amount_collected}/></td>
                </tr>
            ) : null}
            {shouldShowValue(sampleEnvironment.treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_environment.treatment"/></th>
                    <td><Value mbGoRefer="inner" labelType="text" value={sampleEnvironment.treatment}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
