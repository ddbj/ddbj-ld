import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepPlant}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepPlant) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepPlant.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepPlant.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepPlant.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepPlant.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepPlant.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepPlant.reference}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.growth_condition, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.growth_condition"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.growth_condition}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.day_length, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.day_length"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.day_length}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.night_length, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.night_length"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.night_length}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.humidity, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.humidity"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepPlant.humidity}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.day_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.day_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepPlant.day_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.night_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.night_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepPlant.night_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepPlant.temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.light_condition, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.light_condition"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={samplePrepPlant.light_condition}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.sampling_data, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.sampling_data"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.sampling_data}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.sampling_time, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.sampling_time"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.sampling_time}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.sampling_location, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.sampling_location"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.sampling_location}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.watering_regime, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.watering_regime"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.watering_regime}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.nutritional_regime, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.nutritional_regime"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.nutritional_regime}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.growth_medium, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.growth_medium"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.growth_medium}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.growth_location, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.growth_location"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.growth_location}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.plot_design, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.plot_design"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.plot_design}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.sowing_date, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.sowing_date"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.sowing_date}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.metabolism_quenching_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.metabolism_quenching_method"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.metabolism_quenching_method}/>
                    </td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepPlant.sample_storage_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_plant.sample_storage_method"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepPlant.sample_storage_method}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
