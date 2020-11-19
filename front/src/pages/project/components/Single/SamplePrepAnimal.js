import React from 'react'
import {FormattedMessage} from 'react-intl'

import {useShouldShowValue} from '../../../../hooks/project/display'

import Table from './SingleTable'
import {SingleViewValue as Value} from '../Value'

const Single = ({samplePrepAnimal}) => {
    const shouldShowValue = useShouldShowValue()

    if (!samplePrepAnimal) return null

    return (
        <Table>
            <tbody>
            {shouldShowValue(samplePrepAnimal.id, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.id"/></th>
                    <td><Value mbGoRefer="inner" labelType="id" value={samplePrepAnimal.id}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.name, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.name"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepAnimal.name}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.name_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.name_ja"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.name_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.description, 'yes') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.description"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepAnimal.description}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.description_ja, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.description_ja"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepAnimal.description_ja}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.reference, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.reference"/></th>
                    <td><Value mbGoRefer="ref" labelType="string" value={samplePrepAnimal.reference}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.day_length, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.day_length"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.day_length}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.night_length, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.night_length"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.night_length}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.humidity, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.humidity"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepAnimal.humidity}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.day_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.day_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepAnimal.day_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.night_temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.night_temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepAnimal.night_temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.temperature, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.temperature"/></th>
                    <td><Value mbGoRefer="no" labelType="string" value={samplePrepAnimal.temperature}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.light_condition, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.light_condition"/></th>
                    <td><Value mbGoRefer="inner" labelType="string" value={samplePrepAnimal.light_condition}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.sampling_date, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.sampling_date"/></th>
                    <td><Value mbGoRefer="" labelType="date" value={samplePrepAnimal.sampling_date}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.sampling_time, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.sampling_time"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.sampling_time}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.sampling_location, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.sampling_location"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.sampling_location}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.breeding_condition, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.breeding_condition"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.breeding_condition}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.acclimation_duration, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.acclimation_duration"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.acclimation_duration}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.cage_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.cage_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.cage_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.cage_cleaning_frequency, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.cage_cleaning_frequency"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.cage_cleaning_frequency}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.feeding, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.feeding"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.feeding}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.food_manufacturer, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.food_manufacturer"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.food_manufacturer}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.water_access, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.water_access"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.water_access}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.water_type, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.water_type"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.water_type}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.water_quality, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.water_quality"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.water_quality}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.enthanasia_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.enthanasia_method"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.enthanasia_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.tissue_collection_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.tissue_collection_method"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepAnimal.tissue_collection_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.tissue_processing_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.tissue_processing_method"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepAnimal.tissue_processing_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.veterinary_treatment, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.veterinary_treatment"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.veterinary_treatment}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.anesthesia, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.anesthesia"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.anesthesia}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.metabolism_quenching_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.metabolism_quenching_method"/></th>
                    <td><Value mbGoRefer="" labelType="text" value={samplePrepAnimal.metabolism_quenching_method}/></td>
                </tr>
            ) : null}
            {shouldShowValue(samplePrepAnimal.sample_storage_method, '') ? (
                <tr>
                    <th><FormattedMessage id="sheet.sample_prep_animal.sample_storage_method"/></th>
                    <td><Value mbGoRefer="" labelType="string" value={samplePrepAnimal.sample_storage_method}/></td>
                </tr>
            ) : null}
            </tbody>
        </Table>
    )
}

export default Single
