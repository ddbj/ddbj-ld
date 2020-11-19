import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({type}) => {
    const shouldShowValue = useShouldShowValue()

    if (!type) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(type.unit_value, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.unit_value"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.unit_value}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.terms, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.terms"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.terms}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.instrument_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.instrument_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.instrument_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.column_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.column_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.column_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.ms_instrument_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.ms_instrument_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.ms_instrument_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.ion_source, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.ion_source"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.ion_source}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.ionisation_polarity, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.ionisation_polarity"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.ionisation_polarity}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.scan_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.scan_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.scan_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.msn_aqcuisition_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.msn_aqcuisition_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.msn_aqcuisition_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.fragmentation_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.fragmentation_method"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.fragmentation_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.data_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.data_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.data_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.data_processing_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.data_processing_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.data_processing_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.data_processing_method_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.data_processing_method_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.data_processing_method_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.annotation_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.annotation_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.annotation_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.data_category, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.data_category"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.data_category}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.gc_column_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.gc_column_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.gc_column_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(type.lc_column_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.type.lc_column_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={type.lc_column_type}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
