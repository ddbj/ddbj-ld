import {useCallback, useMemo} from 'react'
import {useIsDraft} from "./status";
import {useIntl} from "react-intl";

export const MB_SHOW_YES = "yes"
export const MB_SHOW_NO = "no"
export const MB_SHOW_BLANK = ""

export const useShouldShowValue = () => {
    const shouldShowValue = useCallback((value, mbShow) => {
        if (mbShow === MB_SHOW_NO) return false
        if (mbShow === MB_SHOW_YES) return true
        return Array.isArray(value) ? value.some(Boolean) : !!value
    }, [])
    return shouldShowValue
}

export const useProjectHiddenColumns = (projectList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!projectList) return []

        projectList.forEach(row => {
            hasValueColumns.title_ja = hasValueColumns.title_ja || (row.title_ja && row.title_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.person_creator = hasValueColumns.person_creator || (row.person_creator && row.person_creator.length > 0)
            hasValueColumns.person_principal_investigator = hasValueColumns.person_principal_investigator || (row.person_principal_investigator && row.person_principal_investigator.length > 0)
            hasValueColumns.person_submitter = hasValueColumns.person_submitter || (row.person_submitter && row.person_submitter.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
            hasValueColumns.funding_source = hasValueColumns.funding_source || (row.funding_source && row.funding_source.length > 0)
            hasValueColumns.project_related = hasValueColumns.project_related || (row.project_related && row.project_related.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.comment_ja = hasValueColumns.comment_ja || (row.comment_ja && row.comment_ja.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [projectList])

    return hiddenColumns
}

export const useExperimentHiddenColumns = (experimentList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!experimentList) return []

        experimentList.forEach(row => {
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.measurement_type = hasValueColumns.measurement_type || (row.measurement_type && row.measurement_type.length > 0)
            hasValueColumns.technology_type = hasValueColumns.technology_type || (row.technology_type && row.technology_type.length > 0)
            hasValueColumns.technology_platform = hasValueColumns.technology_platform || (row.technology_platform && row.technology_platform.length > 0)
            hasValueColumns.date_time = hasValueColumns.date_time || (row.date_time && row.date_time.length > 0)
            hasValueColumns.person_experimenter = hasValueColumns.person_experimenter || (row.person_experimenter && row.person_experimenter.length > 0)
            hasValueColumns.experimental_design = hasValueColumns.experimental_design || (row.experimental_design && row.experimental_design.length > 0)
            hasValueColumns.data_analysis_id = hasValueColumns.data_analysis_id || (row.data_analysis_id && row.data_analysis_id.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [experimentList])

    return hiddenColumns
}

export const useExperimentalDesignHiddenColumns = (experimentalDesignList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!experimentalDesignList) return []

        experimentalDesignList.forEach(row => {
            hasValueColumns.quality_control = hasValueColumns.quality_control || (row.quality_control && row.quality_control.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [experimentalDesignList])

    return hiddenColumns
}

export const useSamplePrepHiddenColumns = (samplePrepList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepList) return []

        samplePrepList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.category = hasValueColumns.category || (row.category && row.category.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepList])

    return hiddenColumns
}

export const useSamplePrepAnimalHiddenColumns = (samplePrepAnimalList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepAnimalList) return []

        samplePrepAnimalList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
            hasValueColumns.day_length = hasValueColumns.day_length || (row.day_length && row.day_length.length > 0)
            hasValueColumns.night_length = hasValueColumns.night_length || (row.night_length && row.night_length.length > 0)
            hasValueColumns.humidity = hasValueColumns.humidity || (row.humidity && row.humidity.length > 0)
            hasValueColumns.day_temperature = hasValueColumns.day_temperature || (row.day_temperature && row.day_temperature.length > 0)
            hasValueColumns.night_temperature = hasValueColumns.night_temperature || (row.night_temperature && row.night_temperature.length > 0)
            hasValueColumns.temperature = hasValueColumns.temperature || (row.temperature && row.temperature.length > 0)
            hasValueColumns.light_condition = hasValueColumns.light_condition || (row.light_condition && row.light_condition.length > 0)
            hasValueColumns.sampling_date = hasValueColumns.sampling_date || (row.sampling_date && row.sampling_date.length > 0)
            hasValueColumns.sampling_time = hasValueColumns.sampling_time || (row.sampling_time && row.sampling_time.length > 0)
            hasValueColumns.sampling_location = hasValueColumns.sampling_location || (row.sampling_location && row.sampling_location.length > 0)
            hasValueColumns.breeding_condition = hasValueColumns.breeding_condition || (row.breeding_condition && row.breeding_condition.length > 0)
            hasValueColumns.acclimation_duration = hasValueColumns.acclimation_duration || (row.acclimation_duration && row.acclimation_duration.length > 0)
            hasValueColumns.cage_type = hasValueColumns.cage_type || (row.cage_type && row.cage_type.length > 0)
            hasValueColumns.cage_cleaning_frequency = hasValueColumns.cage_cleaning_frequency || (row.cage_cleaning_frequency && row.cage_cleaning_frequency.length > 0)
            hasValueColumns.feeding = hasValueColumns.feeding || (row.feeding && row.feeding.length > 0)
            hasValueColumns.food_manufacturer = hasValueColumns.food_manufacturer || (row.food_manufacturer && row.food_manufacturer.length > 0)
            hasValueColumns.water_access = hasValueColumns.water_access || (row.water_access && row.water_access.length > 0)
            hasValueColumns.water_type = hasValueColumns.water_type || (row.water_type && row.water_type.length > 0)
            hasValueColumns.water_quality = hasValueColumns.water_quality || (row.water_quality && row.water_quality.length > 0)
            hasValueColumns.enthanasia_method = hasValueColumns.enthanasia_method || (row.enthanasia_method && row.enthanasia_method.length > 0)
            hasValueColumns.tissue_collection_method = hasValueColumns.tissue_collection_method || (row.tissue_collection_method && row.tissue_collection_method.length > 0)
            hasValueColumns.tissue_processing_method = hasValueColumns.tissue_processing_method || (row.tissue_processing_method && row.tissue_processing_method.length > 0)
            hasValueColumns.veterinary_treatment = hasValueColumns.veterinary_treatment || (row.veterinary_treatment && row.veterinary_treatment.length > 0)
            hasValueColumns.anesthesia = hasValueColumns.anesthesia || (row.anesthesia && row.anesthesia.length > 0)
            hasValueColumns.metabolism_quenching_method = hasValueColumns.metabolism_quenching_method || (row.metabolism_quenching_method && row.metabolism_quenching_method.length > 0)
            hasValueColumns.sample_storage_method = hasValueColumns.sample_storage_method || (row.sample_storage_method && row.sample_storage_method.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepAnimalList])

    return hiddenColumns
}

export const useSamplePrepPlantHiddenColumns = (samplePrepPlantList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepPlantList) return []

        samplePrepPlantList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
            hasValueColumns.growth_condition = hasValueColumns.growth_condition || (row.growth_condition && row.growth_condition.length > 0)
            hasValueColumns.day_length = hasValueColumns.day_length || (row.day_length && row.day_length.length > 0)
            hasValueColumns.night_length = hasValueColumns.night_length || (row.night_length && row.night_length.length > 0)
            hasValueColumns.humidity = hasValueColumns.humidity || (row.humidity && row.humidity.length > 0)
            hasValueColumns.day_temperature = hasValueColumns.day_temperature || (row.day_temperature && row.day_temperature.length > 0)
            hasValueColumns.night_temperature = hasValueColumns.night_temperature || (row.night_temperature && row.night_temperature.length > 0)
            hasValueColumns.temperature = hasValueColumns.temperature || (row.temperature && row.temperature.length > 0)
            hasValueColumns.light_condition = hasValueColumns.light_condition || (row.light_condition && row.light_condition.length > 0)
            hasValueColumns.sampling_data = hasValueColumns.sampling_data || (row.sampling_data && row.sampling_data.length > 0)
            hasValueColumns.sampling_time = hasValueColumns.sampling_time || (row.sampling_time && row.sampling_time.length > 0)
            hasValueColumns.sampling_location = hasValueColumns.sampling_location || (row.sampling_location && row.sampling_location.length > 0)
            hasValueColumns.watering_regime = hasValueColumns.watering_regime || (row.watering_regime && row.watering_regime.length > 0)
            hasValueColumns.nutritional_regime = hasValueColumns.nutritional_regime || (row.nutritional_regime && row.nutritional_regime.length > 0)
            hasValueColumns.growth_medium = hasValueColumns.growth_medium || (row.growth_medium && row.growth_medium.length > 0)
            hasValueColumns.growth_location = hasValueColumns.growth_location || (row.growth_location && row.growth_location.length > 0)
            hasValueColumns.plot_design = hasValueColumns.plot_design || (row.plot_design && row.plot_design.length > 0)
            hasValueColumns.sowing_date = hasValueColumns.sowing_date || (row.sowing_date && row.sowing_date.length > 0)
            hasValueColumns.metabolism_quenching_method = hasValueColumns.metabolism_quenching_method || (row.metabolism_quenching_method && row.metabolism_quenching_method.length > 0)
            hasValueColumns.sample_storage_method = hasValueColumns.sample_storage_method || (row.sample_storage_method && row.sample_storage_method.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepPlantList])

    return hiddenColumns
}

export const useSamplePrepChemicalHiddenColumns = (samplePrepChemicalList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepChemicalList) return []

        samplePrepChemicalList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepChemicalList])

    return hiddenColumns
}

export const useSamplePrepOtherHiddenColumns = (samplePrepOtherList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepOtherList) return []

        samplePrepOtherList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepOtherList])

    return hiddenColumns
}

export const useLightConditionHiddenColumns = (lightConditionList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!lightConditionList) return []

        lightConditionList.forEach(row => {
            hasValueColumns.light_quality = hasValueColumns.light_quality || (row.light_quality && row.light_quality.length > 0)
            hasValueColumns.light_intensity = hasValueColumns.light_intensity || (row.light_intensity && row.light_intensity.length > 0)
            hasValueColumns.peak_wave_length = hasValueColumns.peak_wave_length || (row.peak_wave_length && row.peak_wave_length.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [lightConditionList])

    return hiddenColumns
}

export const useSamplePrepClinicalHiddenColumns = (samplePrepClinicalList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepClinicalList) return []

        samplePrepClinicalList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepClinicalList])

    return hiddenColumns
}

export const useSamplePrepBacteriaHiddenColumns = (samplePrepBacteriaList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepBacteriaList) return []

        samplePrepBacteriaList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepBacteriaList])

    return hiddenColumns
}

export const useSamplePrepEnvironmentHiddenColumns = (samplePrepEnvironmentList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepEnvironmentList) return []

        samplePrepEnvironmentList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepEnvironmentList])

    return hiddenColumns
}

export const useSamplePrepFoodHiddenColumns = (samplePrepFoodList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepFoodList) return []

        samplePrepFoodList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepFoodList])

    return hiddenColumns
}

export const useSamplePrepControlHiddenColumns = (samplePrepControlList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePrepControlList) return []

        samplePrepControlList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePrepControlList])

    return hiddenColumns
}

export const useSampleHiddenColumns = (sampleList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleList) return []

        sampleList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.category = hasValueColumns.category || (row.category && row.category.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleList])

    return hiddenColumns
}

export const useSampleAnimalHiddenColumns = (sampleAnimalList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleAnimalList) return []

        sampleAnimalList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
            hasValueColumns.bio_sample_id = hasValueColumns.bio_sample_id || (row.bio_sample_id && row.bio_sample_id.length > 0)
            hasValueColumns.species_name = hasValueColumns.species_name || (row.species_name && row.species_name.length > 0)
            hasValueColumns.species_name_ja = hasValueColumns.species_name_ja || (row.species_name_ja && row.species_name_ja.length > 0)
            hasValueColumns.taxonomy_ncbi = hasValueColumns.taxonomy_ncbi || (row.taxonomy_ncbi && row.taxonomy_ncbi.length > 0)
            hasValueColumns.cultivar = hasValueColumns.cultivar || (row.cultivar && row.cultivar.length > 0)
            hasValueColumns.cultivar_ja = hasValueColumns.cultivar_ja || (row.cultivar_ja && row.cultivar_ja.length > 0)
            hasValueColumns.genotype = hasValueColumns.genotype || (row.genotype && row.genotype.length > 0)
            hasValueColumns.biomaterial_organ = hasValueColumns.biomaterial_organ || (row.biomaterial_organ && row.biomaterial_organ.length > 0)
            hasValueColumns.biomaterial_tissue = hasValueColumns.biomaterial_tissue || (row.biomaterial_tissue && row.biomaterial_tissue.length > 0)
            hasValueColumns.mutant = hasValueColumns.mutant || (row.mutant && row.mutant.length > 0)
            hasValueColumns.transgenic_line = hasValueColumns.transgenic_line || (row.transgenic_line && row.transgenic_line.length > 0)
            hasValueColumns.related_gene = hasValueColumns.related_gene || (row.related_gene && row.related_gene.length > 0)
            hasValueColumns.biomaterial_line = hasValueColumns.biomaterial_line || (row.biomaterial_line && row.biomaterial_line.length > 0)
            hasValueColumns.phenotype = hasValueColumns.phenotype || (row.phenotype && row.phenotype.length > 0)
            hasValueColumns.phenotypic_sex = hasValueColumns.phenotypic_sex || (row.phenotypic_sex && row.phenotypic_sex.length > 0)
            hasValueColumns.disease = hasValueColumns.disease || (row.disease && row.disease.length > 0)
            hasValueColumns.clinical_signs = hasValueColumns.clinical_signs || (row.clinical_signs && row.clinical_signs.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleAnimalList])

    return hiddenColumns
}

export const useSamplePlantHiddenColumns = (samplePlantList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!samplePlantList) return []

        samplePlantList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
            hasValueColumns.bio_sample_id = hasValueColumns.bio_sample_id || (row.bio_sample_id && row.bio_sample_id.length > 0)
            hasValueColumns.species_name = hasValueColumns.species_name || (row.species_name && row.species_name.length > 0)
            hasValueColumns.species_name_ja = hasValueColumns.species_name_ja || (row.species_name_ja && row.species_name_ja.length > 0)
            hasValueColumns.taxonomy_ncbi = hasValueColumns.taxonomy_ncbi || (row.taxonomy_ncbi && row.taxonomy_ncbi.length > 0)
            hasValueColumns.cultivar = hasValueColumns.cultivar || (row.cultivar && row.cultivar.length > 0)
            hasValueColumns.cultivar_ja = hasValueColumns.cultivar_ja || (row.cultivar_ja && row.cultivar_ja.length > 0)
            hasValueColumns.genotype = hasValueColumns.genotype || (row.genotype && row.genotype.length > 0)
            hasValueColumns.developmental_stage = hasValueColumns.developmental_stage || (row.developmental_stage && row.developmental_stage.length > 0)
            hasValueColumns.biomaterial_organ = hasValueColumns.biomaterial_organ || (row.biomaterial_organ && row.biomaterial_organ.length > 0)
            hasValueColumns.biomaterial_tissue = hasValueColumns.biomaterial_tissue || (row.biomaterial_tissue && row.biomaterial_tissue.length > 0)
            hasValueColumns.mutant = hasValueColumns.mutant || (row.mutant && row.mutant.length > 0)
            hasValueColumns.transgenic_line = hasValueColumns.transgenic_line || (row.transgenic_line && row.transgenic_line.length > 0)
            hasValueColumns.related_gene = hasValueColumns.related_gene || (row.related_gene && row.related_gene.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [samplePlantList])

    return hiddenColumns
}

export const useSampleChemicalHiddenColumns = (sampleChemicalList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleChemicalList) return []

        sampleChemicalList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
            hasValueColumns.chemical_formula = hasValueColumns.chemical_formula || (row.chemical_formula && row.chemical_formula.length > 0)
            hasValueColumns.smiles = hasValueColumns.smiles || (row.smiles && row.smiles.length > 0)
            hasValueColumns.inchi = hasValueColumns.inchi || (row.inchi && row.inchi.length > 0)
            hasValueColumns.inchi_key = hasValueColumns.inchi_key || (row.inchi_key && row.inchi_key.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleChemicalList])

    return hiddenColumns
}

export const useSampleOtherHiddenColumns = (sampleOtherList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleOtherList) return []

        sampleOtherList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleOtherList])

    return hiddenColumns
}

export const useSampleClinicalHiddenColumns = (sampleClinicalList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleClinicalList) return []

        sampleClinicalList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleClinicalList])

    return hiddenColumns
}

export const useSampleBacteriaHiddenColumns = (sampleBacteriaList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleBacteriaList) return []

        sampleBacteriaList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleBacteriaList])

    return hiddenColumns
}

export const useSampleEnvironmentHiddenColumns = (sampleEnvironmentList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleEnvironmentList) return []

        sampleEnvironmentList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleEnvironmentList])

    return hiddenColumns
}

export const useSampleFoodHiddenColumns = (sampleFoodList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleFoodList) return []

        sampleFoodList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleFoodList])

    return hiddenColumns
}

export const useSampleControlHiddenColumns = (sampleControlList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!sampleControlList) return []

        sampleControlList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.supplier_product_id = hasValueColumns.supplier_product_id || (row.supplier_product_id && row.supplier_product_id.length > 0)
            hasValueColumns.amount_collected = hasValueColumns.amount_collected || (row.amount_collected && row.amount_collected.length > 0)
            hasValueColumns.treatment = hasValueColumns.treatment || (row.treatment && row.treatment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [sampleControlList])

    return hiddenColumns
}

export const useTreatmentHiddenColumns = (treatmentList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!treatmentList) return []

        treatmentList.forEach(row => {
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.concentration = hasValueColumns.concentration || (row.concentration && row.concentration.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [treatmentList])

    return hiddenColumns
}

export const useExtractionMethodHiddenColumns = (extractionMethodList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!extractionMethodList) return []

        extractionMethodList.forEach(row => {
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.internal_standard = hasValueColumns.internal_standard || (row.internal_standard && row.internal_standard.length > 0)
            hasValueColumns.derivatisation = hasValueColumns.derivatisation || (row.derivatisation && row.derivatisation.length > 0)
            hasValueColumns.extract_concentration = hasValueColumns.extract_concentration || (row.extract_concentration && row.extract_concentration.length > 0)
            hasValueColumns.extract_storage_method = hasValueColumns.extract_storage_method || (row.extract_storage_method && row.extract_storage_method.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [extractionMethodList])

    return hiddenColumns
}

export const useMeasurementMethodHiddenColumns = (measurementMethodList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementMethodList) return []

        measurementMethodList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.category = hasValueColumns.category || (row.category && row.category.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [measurementMethodList])

    return hiddenColumns
}

export const useMeasurementMethodLcmsHiddenColumns = (measurementMethodLcmsList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementMethodLcmsList) return []

        measurementMethodLcmsList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.other_condition_name = hasValueColumns.other_condition_name || (row.other_condition_name && row.other_condition_name.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'lc_condition_id',
            'ms_condition_id',
            'other_condition_id',
        ])
    }, [measurementMethodLcmsList])

    return hiddenColumns
}

export const useMeasurementMethodGcmsHiddenColumns = (measurementMethodGcmsList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementMethodGcmsList) return []

        measurementMethodGcmsList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.other_condition_name = hasValueColumns.other_condition_name || (row.other_condition_name && row.other_condition_name.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'gc_condition_id',
            'ms_condition_id',
            'other_condition_id',
        ])
    }, [measurementMethodGcmsList])

    return hiddenColumns
}

export const useMeasurementMethodCemsHiddenColumns = (measurementMethodCemsList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementMethodCemsList) return []

        measurementMethodCemsList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.other_condition_name = hasValueColumns.other_condition_name || (row.other_condition_name && row.other_condition_name.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'ce_condition_id',
            'ms_condition_id',
            'other_condition_id',
        ])
    }, [measurementMethodCemsList])

    return hiddenColumns
}

export const useMeasurementMethodMsHiddenColumns = (measurementMethodMsList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementMethodMsList) return []

        measurementMethodMsList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.other_condition_name = hasValueColumns.other_condition_name || (row.other_condition_name && row.other_condition_name.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'ms_condition_id',
            'other_condition_id',
        ])
    }, [measurementMethodMsList])

    return hiddenColumns
}

export const useMeasurementMethodNmrHiddenColumns = (measurementMethodNmrList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementMethodNmrList) return []

        measurementMethodNmrList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.other_condition_name = hasValueColumns.other_condition_name || (row.other_condition_name && row.other_condition_name.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'nmr_condition_id',
            'other_condition_id',
        ])
    }, [measurementMethodNmrList])

    return hiddenColumns
}

export const useMeasurementMethodOtherHiddenColumns = (measurementMethodOtherList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementMethodOtherList) return []

        measurementMethodOtherList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.other_condition_name = hasValueColumns.other_condition_name || (row.other_condition_name && row.other_condition_name.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'other_condition_id',
        ])
    }, [measurementMethodOtherList])

    return hiddenColumns
}

export const useConditionLcHiddenColumns = (conditionLcList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!conditionLcList) return []

        conditionLcList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.instrument = hasValueColumns.instrument || (row.instrument && row.instrument.length > 0)
            hasValueColumns.column = hasValueColumns.column || (row.column && row.column.length > 0)
            hasValueColumns.column_type = hasValueColumns.column_type || (row.column_type && row.column_type.length > 0)
            hasValueColumns.column_other = hasValueColumns.column_other || (row.column_other && row.column_other.length > 0)
            hasValueColumns.column_temperature = hasValueColumns.column_temperature || (row.column_temperature && row.column_temperature.length > 0)
            hasValueColumns.column_pressure = hasValueColumns.column_pressure || (row.column_pressure && row.column_pressure.length > 0)
            hasValueColumns.solvent_a = hasValueColumns.solvent_a || (row.solvent_a && row.solvent_a.length > 0)
            hasValueColumns.solvent_b = hasValueColumns.solvent_b || (row.solvent_b && row.solvent_b.length > 0)
            hasValueColumns.solvent_other = hasValueColumns.solvent_other || (row.solvent_other && row.solvent_other.length > 0)
            hasValueColumns.flow_gradient = hasValueColumns.flow_gradient || (row.flow_gradient && row.flow_gradient.length > 0)
            hasValueColumns.flow_rate = hasValueColumns.flow_rate || (row.flow_rate && row.flow_rate.length > 0)
            hasValueColumns.elution_detector = hasValueColumns.elution_detector || (row.elution_detector && row.elution_detector.length > 0)
            hasValueColumns.elution_detector_wave_length_min = hasValueColumns.elution_detector_wave_length_min || (row.elution_detector_wave_length_min && row.elution_detector_wave_length_min.length > 0)
            hasValueColumns.elution_detector_wave_length_max = hasValueColumns.elution_detector_wave_length_max || (row.elution_detector_wave_length_max && row.elution_detector_wave_length_max.length > 0)
            hasValueColumns.analytical_time = hasValueColumns.analytical_time || (row.analytical_time && row.analytical_time.length > 0)
            hasValueColumns.control_software = hasValueColumns.control_software || (row.control_software && row.control_software.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [conditionLcList])

    return hiddenColumns
}

export const useConditionGcHiddenColumns = (conditionGcList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!conditionGcList) return []

        conditionGcList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.instrument = hasValueColumns.instrument || (row.instrument && row.instrument.length > 0)
            hasValueColumns.column = hasValueColumns.column || (row.column && row.column.length > 0)
            hasValueColumns.column_type = hasValueColumns.column_type || (row.column_type && row.column_type.length > 0)
            hasValueColumns.column_other = hasValueColumns.column_other || (row.column_other && row.column_other.length > 0)
            hasValueColumns.column_temperature = hasValueColumns.column_temperature || (row.column_temperature && row.column_temperature.length > 0)
            hasValueColumns.column_pressure = hasValueColumns.column_pressure || (row.column_pressure && row.column_pressure.length > 0)
            hasValueColumns.temperature_gradient = hasValueColumns.temperature_gradient || (row.temperature_gradient && row.temperature_gradient.length > 0)
            hasValueColumns.flow_gradient = hasValueColumns.flow_gradient || (row.flow_gradient && row.flow_gradient.length > 0)
            hasValueColumns.flow_rate = hasValueColumns.flow_rate || (row.flow_rate && row.flow_rate.length > 0)
            hasValueColumns.flow_gas = hasValueColumns.flow_gas || (row.flow_gas && row.flow_gas.length > 0)
            hasValueColumns.analytical_time = hasValueColumns.analytical_time || (row.analytical_time && row.analytical_time.length > 0)
            hasValueColumns.control_software = hasValueColumns.control_software || (row.control_software && row.control_software.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [conditionGcList])

    return hiddenColumns
}

export const useConditionCeHiddenColumns = (conditionCeList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!conditionCeList) return []

        conditionCeList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.instrument = hasValueColumns.instrument || (row.instrument && row.instrument.length > 0)
            hasValueColumns.column = hasValueColumns.column || (row.column && row.column.length > 0)
            hasValueColumns.column_type = hasValueColumns.column_type || (row.column_type && row.column_type.length > 0)
            hasValueColumns.column_other = hasValueColumns.column_other || (row.column_other && row.column_other.length > 0)
            hasValueColumns.column_temperature = hasValueColumns.column_temperature || (row.column_temperature && row.column_temperature.length > 0)
            hasValueColumns.column_pressure = hasValueColumns.column_pressure || (row.column_pressure && row.column_pressure.length > 0)
            hasValueColumns.solvent = hasValueColumns.solvent || (row.solvent && row.solvent.length > 0)
            hasValueColumns.flow_gradient = hasValueColumns.flow_gradient || (row.flow_gradient && row.flow_gradient.length > 0)
            hasValueColumns.flow_rate = hasValueColumns.flow_rate || (row.flow_rate && row.flow_rate.length > 0)
            hasValueColumns.elution_detector = hasValueColumns.elution_detector || (row.elution_detector && row.elution_detector.length > 0)
            hasValueColumns.elution_detector_wave_length_min = hasValueColumns.elution_detector_wave_length_min || (row.elution_detector_wave_length_min && row.elution_detector_wave_length_min.length > 0)
            hasValueColumns.elution_detector_wave_length_max = hasValueColumns.elution_detector_wave_length_max || (row.elution_detector_wave_length_max && row.elution_detector_wave_length_max.length > 0)
            hasValueColumns.analytical_time = hasValueColumns.analytical_time || (row.analytical_time && row.analytical_time.length > 0)
            hasValueColumns.control_software = hasValueColumns.control_software || (row.control_software && row.control_software.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [conditionCeList])

    return hiddenColumns
}

export const useConditionMsHiddenColumns = (conditionMsList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!conditionMsList) return []

        conditionMsList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.instrument = hasValueColumns.instrument || (row.instrument && row.instrument.length > 0)
            hasValueColumns.ms_instrument_type = hasValueColumns.ms_instrument_type || (row.ms_instrument_type && row.ms_instrument_type.length > 0)
            hasValueColumns.ion_source = hasValueColumns.ion_source || (row.ion_source && row.ion_source.length > 0)
            hasValueColumns.ionization_polarity = hasValueColumns.ionization_polarity || (row.ionization_polarity && row.ionization_polarity.length > 0)
            hasValueColumns.ionization_energy = hasValueColumns.ionization_energy || (row.ionization_energy && row.ionization_energy.length > 0)
            hasValueColumns.scan_type = hasValueColumns.scan_type || (row.scan_type && row.scan_type.length > 0)
            hasValueColumns.full_scan_mz_range = hasValueColumns.full_scan_mz_range || (row.full_scan_mz_range && row.full_scan_mz_range.length > 0)
            hasValueColumns.ms_acquisition_rate = hasValueColumns.ms_acquisition_rate || (row.ms_acquisition_rate && row.ms_acquisition_rate.length > 0)
            hasValueColumns.msn_acquisition_method = hasValueColumns.msn_acquisition_method || (row.msn_acquisition_method && row.msn_acquisition_method.length > 0)
            hasValueColumns.scan_program_details = hasValueColumns.scan_program_details || (row.scan_program_details && row.scan_program_details.length > 0)
            hasValueColumns.resolution = hasValueColumns.resolution || (row.resolution && row.resolution.length > 0)
            hasValueColumns.mz_accuracy_full_scan = hasValueColumns.mz_accuracy_full_scan || (row.mz_accuracy_full_scan && row.mz_accuracy_full_scan.length > 0)
            hasValueColumns.mz_accuracy_msn_scan = hasValueColumns.mz_accuracy_msn_scan || (row.mz_accuracy_msn_scan && row.mz_accuracy_msn_scan.length > 0)
            hasValueColumns.capillary_temperature = hasValueColumns.capillary_temperature || (row.capillary_temperature && row.capillary_temperature.length > 0)
            hasValueColumns.collision_energy = hasValueColumns.collision_energy || (row.collision_energy && row.collision_energy.length > 0)
            hasValueColumns.ion_source_temperature = hasValueColumns.ion_source_temperature || (row.ion_source_temperature && row.ion_source_temperature.length > 0)
            hasValueColumns.ion_spray_voltage = hasValueColumns.ion_spray_voltage || (row.ion_spray_voltage && row.ion_spray_voltage.length > 0)
            hasValueColumns.fragmentation_method = hasValueColumns.fragmentation_method || (row.fragmentation_method && row.fragmentation_method.length > 0)
            hasValueColumns.retention_index_method = hasValueColumns.retention_index_method || (row.retention_index_method && row.retention_index_method.length > 0)
            hasValueColumns.desolvation_temperature = hasValueColumns.desolvation_temperature || (row.desolvation_temperature && row.desolvation_temperature.length > 0)
            hasValueColumns.sheath_gas = hasValueColumns.sheath_gas || (row.sheath_gas && row.sheath_gas.length > 0)
            hasValueColumns.control_software = hasValueColumns.control_software || (row.control_software && row.control_software.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [conditionMsList])

    return hiddenColumns
}

export const useConditionNmrHiddenColumns = (conditionNmrList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!conditionNmrList) return []

        conditionNmrList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.instrument = hasValueColumns.instrument || (row.instrument && row.instrument.length > 0)
            hasValueColumns.control_software = hasValueColumns.control_software || (row.control_software && row.control_software.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [conditionNmrList])

    return hiddenColumns
}

export const useConditionOtherHiddenColumns = (conditionOtherList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!conditionOtherList) return []

        conditionOtherList.forEach(row => {
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.description_ja = hasValueColumns.description_ja || (row.description_ja && row.description_ja.length > 0)
            hasValueColumns.instrument = hasValueColumns.instrument || (row.instrument && row.instrument.length > 0)
            hasValueColumns.control_software = hasValueColumns.control_software || (row.control_software && row.control_software.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [conditionOtherList])

    return hiddenColumns
}

export const useInstrumentHiddenColumns = (instrumentList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!instrumentList) return []

        instrumentList.forEach(row => {
            hasValueColumns.type = hasValueColumns.type || (row.type && row.type.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.product_name = hasValueColumns.product_name || (row.product_name && row.product_name.length > 0)
            hasValueColumns.product_id = hasValueColumns.product_id || (row.product_id && row.product_id.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [instrumentList])

    return hiddenColumns
}

export const useColumnHiddenColumns = (columnList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!columnList) return []

        columnList.forEach(row => {
            hasValueColumns.type = hasValueColumns.type || (row.type && row.type.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.product_name = hasValueColumns.product_name || (row.product_name && row.product_name.length > 0)
            hasValueColumns.product_id = hasValueColumns.product_id || (row.product_id && row.product_id.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [columnList])

    return hiddenColumns
}

export const useMeasurementHiddenColumns = (measurementList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!measurementList) return []

        measurementList.forEach(row => {
            hasValueColumns.date_time = hasValueColumns.date_time || (row.date_time && row.date_time.length > 0)
            hasValueColumns.person_experimenter = hasValueColumns.person_experimenter || (row.person_experimenter && row.person_experimenter.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [measurementList])

    return hiddenColumns
}

export const useRawDataFileHiddenColumns = (rawDataFileList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!rawDataFileList) return []

        rawDataFileList.forEach(row => {
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.file_name = hasValueColumns.file_name || (row.file_name && row.file_name.length > 0)
            hasValueColumns.file_format = hasValueColumns.file_format || (row.file_format && row.file_format.length > 0)
            hasValueColumns.download_url = hasValueColumns.download_url || (row.download_url && row.download_url.length > 0)
            hasValueColumns.md5 = hasValueColumns.md5 || (row.md5 && row.md5.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'data_type_id',
            'local_folder_path',
        ])
    }, [rawDataFileList])

    return hiddenColumns
}

export const useDataAnalysisHiddenColumns = (dataAnalysisList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!dataAnalysisList) return []

        dataAnalysisList.forEach(row => {
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.data_preprocessing = hasValueColumns.data_preprocessing || (row.data_preprocessing && row.data_preprocessing.length > 0)
            hasValueColumns.statictical_analysis = hasValueColumns.statictical_analysis || (row.statictical_analysis && row.statictical_analysis.length > 0)
            hasValueColumns.univariate_analysis = hasValueColumns.univariate_analysis || (row.univariate_analysis && row.univariate_analysis.length > 0)
            hasValueColumns.multivariate_analysis = hasValueColumns.multivariate_analysis || (row.multivariate_analysis && row.multivariate_analysis.length > 0)
            hasValueColumns.visualisation = hasValueColumns.visualisation || (row.visualisation && row.visualisation.length > 0)
            hasValueColumns.annotation_method = hasValueColumns.annotation_method || (row.annotation_method && row.annotation_method.length > 0)
            hasValueColumns.recommended_decimal_place_mz = hasValueColumns.recommended_decimal_place_mz || (row.recommended_decimal_place_mz && row.recommended_decimal_place_mz.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [dataAnalysisList])

    return hiddenColumns
}

export const useAnalyzedRawFileHiddenColumns = (analyzedRawFileList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!analyzedRawFileList) return []

        analyzedRawFileList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.project_id = hasValueColumns.project_id || (row.project_id && row.project_id.length > 0)
            hasValueColumns.category = hasValueColumns.category || (row.category && row.category.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [analyzedRawFileList])

    return hiddenColumns
}

export const useDataPreprocessingHiddenColumns = (dataPreprocessingList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!dataPreprocessingList) return []

        dataPreprocessingList.forEach(row => {
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.general = hasValueColumns.general || (row.general && row.general.length > 0)
            hasValueColumns.peak_detection = hasValueColumns.peak_detection || (row.peak_detection && row.peak_detection.length > 0)
            hasValueColumns.peak_alignment = hasValueColumns.peak_alignment || (row.peak_alignment && row.peak_alignment.length > 0)
            hasValueColumns.spectral_extraction = hasValueColumns.spectral_extraction || (row.spectral_extraction && row.spectral_extraction.length > 0)
            hasValueColumns.retention_time_correction = hasValueColumns.retention_time_correction || (row.retention_time_correction && row.retention_time_correction.length > 0)
            hasValueColumns.summarisation = hasValueColumns.summarisation || (row.summarisation && row.summarisation.length > 0)
            hasValueColumns.normalisation = hasValueColumns.normalisation || (row.normalisation && row.normalisation.length > 0)
            hasValueColumns.data_transformation = hasValueColumns.data_transformation || (row.data_transformation && row.data_transformation.length > 0)
            hasValueColumns.scaling = hasValueColumns.scaling || (row.scaling && row.scaling.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [dataPreprocessingList])

    return hiddenColumns
}

export const useDataProcessingMethodHiddenColumns = (dataProcessingMethodList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!dataProcessingMethodList) return []

        dataProcessingMethodList.forEach(row => {
            hasValueColumns.type = hasValueColumns.type || (row.type && row.type.length > 0)
            hasValueColumns.method_type = hasValueColumns.method_type || (row.method_type && row.method_type.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.software = hasValueColumns.software || (row.software && row.software.length > 0)
            hasValueColumns.parameter = hasValueColumns.parameter || (row.parameter && row.parameter.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [dataProcessingMethodList])

    return hiddenColumns
}

export const useDataProcessingMethodTypeHiddenColumns = (dataProcessingMethodTypeList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!dataProcessingMethodTypeList) return []

        dataProcessingMethodTypeList.forEach(row => {
            hasValueColumns.type = hasValueColumns.type || (row.type && row.type.length > 0)
            hasValueColumns.ontology_uri = hasValueColumns.ontology_uri || (row.ontology_uri && row.ontology_uri.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [dataProcessingMethodTypeList])

    return hiddenColumns
}

export const useAnnotationMethodHiddenColumns = (annotationMethodList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!annotationMethodList) return []

        annotationMethodList.forEach(row => {
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.software = hasValueColumns.software || (row.software && row.software.length > 0)
            hasValueColumns.parameters = hasValueColumns.parameters || (row.parameters && row.parameters.length > 0)
            hasValueColumns.spectral_library = hasValueColumns.spectral_library || (row.spectral_library && row.spectral_library.length > 0)
            hasValueColumns.evidence = hasValueColumns.evidence || (row.evidence && row.evidence.length > 0)
            hasValueColumns.annotation_type = hasValueColumns.annotation_type || (row.annotation_type && row.annotation_type.length > 0)
            hasValueColumns.reference = hasValueColumns.reference || (row.reference && row.reference.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [annotationMethodList])

    return hiddenColumns
}

export const useAnalyzedResultFileHiddenColumns = (analyzedResultFileList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!analyzedResultFileList) return []

        analyzedResultFileList.forEach(row => {
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.file_name = hasValueColumns.file_name || (row.file_name && row.file_name.length > 0)
            hasValueColumns.file_format = hasValueColumns.file_format || (row.file_format && row.file_format.length > 0)
            hasValueColumns.download_url = hasValueColumns.download_url || (row.download_url && row.download_url.length > 0)
            hasValueColumns.md5 = hasValueColumns.md5 || (row.md5 && row.md5.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'data_type_id',
            'local_folder_path',
        ])
    }, [analyzedResultFileList])

    return hiddenColumns
}

export const usePersonHiddenColumns = (personList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!personList) return []

        personList.forEach(row => {
            hasValueColumns.label_ja = hasValueColumns.label_ja || (row.label_ja && row.label_ja.length > 0)
            hasValueColumns.name_first = hasValueColumns.name_first || (row.name_first && row.name_first.length > 0)
            hasValueColumns.name_family_ja = hasValueColumns.name_family_ja || (row.name_family_ja && row.name_family_ja.length > 0)
            hasValueColumns.name_first_ja = hasValueColumns.name_first_ja || (row.name_first_ja && row.name_first_ja.length > 0)
            hasValueColumns.name_middle = hasValueColumns.name_middle || (row.name_middle && row.name_middle.length > 0)
            hasValueColumns.organisation = hasValueColumns.organisation || (row.organisation && row.organisation.length > 0)
            hasValueColumns.email = hasValueColumns.email || (row.email && row.email.length > 0)
            hasValueColumns.orcid = hasValueColumns.orcid || (row.orcid && row.orcid.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
            'name_family',
        ])
    }, [personList])

    return hiddenColumns
}

export const useOrganisationHiddenColumns = (organisationList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!organisationList) return []

        organisationList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.name_ja = hasValueColumns.name_ja || (row.name_ja && row.name_ja.length > 0)
            hasValueColumns.abbreviation = hasValueColumns.abbreviation || (row.abbreviation && row.abbreviation.length > 0)
            hasValueColumns.homepage = hasValueColumns.homepage || (row.homepage && row.homepage.length > 0)
            hasValueColumns.address = hasValueColumns.address || (row.address && row.address.length > 0)
            hasValueColumns.address_ja = hasValueColumns.address_ja || (row.address_ja && row.address_ja.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [organisationList])

    return hiddenColumns
}

export const useFileHiddenColumns = (fileList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!fileList) return []

        fileList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
            hasValueColumns.local_folder_path = hasValueColumns.local_folder_path || (row.local_folder_path && row.local_folder_path.length > 0)
            hasValueColumns.file_name = hasValueColumns.file_name || (row.file_name && row.file_name.length > 0)
            hasValueColumns.file_format = hasValueColumns.file_format || (row.file_format && row.file_format.length > 0)
            hasValueColumns.download_url = hasValueColumns.download_url || (row.download_url && row.download_url.length > 0)
            hasValueColumns.md5 = hasValueColumns.md5 || (row.md5 && row.md5.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [fileList])

    return hiddenColumns
}

export const useFileFormatHiddenColumns = (fileFormatList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!fileFormatList) return []

        fileFormatList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [fileFormatList])

    return hiddenColumns
}

export const useUnitHiddenColumns = (unitList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!unitList) return []

        unitList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.super_class = hasValueColumns.super_class || (row.super_class && row.super_class.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [unitList])

    return hiddenColumns
}

export const useUnitValueHiddenColumns = (unitValueList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!unitValueList) return []

        unitValueList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.type = hasValueColumns.type || (row.type && row.type.length > 0)
            hasValueColumns.value = hasValueColumns.value || (row.value && row.value.length > 0)
            hasValueColumns.unit = hasValueColumns.unit || (row.unit && row.unit.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [unitValueList])

    return hiddenColumns
}

export const useReferenceHiddenColumns = (referenceList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!referenceList) return []

        referenceList.forEach(row => {
            hasValueColumns.title_ja = hasValueColumns.title_ja || (row.title_ja && row.title_ja.length > 0)
            hasValueColumns.authors_ja = hasValueColumns.authors_ja || (row.authors_ja && row.authors_ja.length > 0)
            hasValueColumns.journal_ja = hasValueColumns.journal_ja || (row.journal_ja && row.journal_ja.length > 0)
            hasValueColumns.volume = hasValueColumns.volume || (row.volume && row.volume.length > 0)
            hasValueColumns.issue = hasValueColumns.issue || (row.issue && row.issue.length > 0)
            hasValueColumns.pages = hasValueColumns.pages || (row.pages && row.pages.length > 0)
            hasValueColumns.doi = hasValueColumns.doi || (row.doi && row.doi.length > 0)
            hasValueColumns.pmid = hasValueColumns.pmid || (row.pmid && row.pmid.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'citation_label',
        ])
    }, [referenceList])

    return hiddenColumns
}

export const useSoftwareHiddenColumns = (softwareList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!softwareList) return []

        softwareList.forEach(row => {
            hasValueColumns.id = hasValueColumns.id || (row.id && row.id.length > 0)
            hasValueColumns.name = hasValueColumns.name || (row.name && row.name.length > 0)
            hasValueColumns.version = hasValueColumns.version || (row.version && row.version.length > 0)
            hasValueColumns.supplier = hasValueColumns.supplier || (row.supplier && row.supplier.length > 0)
            hasValueColumns.product_id = hasValueColumns.product_id || (row.product_id && row.product_id.length > 0)
            hasValueColumns.available_url = hasValueColumns.available_url || (row.available_url && row.available_url.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [softwareList])

    return hiddenColumns
}

export const useTermHiddenColumns = (termList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!termList) return []

        termList.forEach(row => {
            hasValueColumns.type = hasValueColumns.type || (row.type && row.type.length > 0)
            hasValueColumns.ontology_uri = hasValueColumns.ontology_uri || (row.ontology_uri && row.ontology_uri.length > 0)
            hasValueColumns.comment = hasValueColumns.comment || (row.comment && row.comment.length > 0)
            hasValueColumns.description = hasValueColumns.description || (row.description && row.description.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'id',
        ])
    }, [termList])

    return hiddenColumns
}

export const useTypeHiddenColumns = (typeList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!typeList) return []

        typeList.forEach(row => {
            hasValueColumns.unit_value = hasValueColumns.unit_value || (row.unit_value && row.unit_value.length > 0)
            hasValueColumns.terms = hasValueColumns.terms || (row.terms && row.terms.length > 0)
            hasValueColumns.instrument_type = hasValueColumns.instrument_type || (row.instrument_type && row.instrument_type.length > 0)
            hasValueColumns.column_type = hasValueColumns.column_type || (row.column_type && row.column_type.length > 0)
            hasValueColumns.ms_instrument_type = hasValueColumns.ms_instrument_type || (row.ms_instrument_type && row.ms_instrument_type.length > 0)
            hasValueColumns.ion_source = hasValueColumns.ion_source || (row.ion_source && row.ion_source.length > 0)
            hasValueColumns.ionisation_polarity = hasValueColumns.ionisation_polarity || (row.ionisation_polarity && row.ionisation_polarity.length > 0)
            hasValueColumns.scan_type = hasValueColumns.scan_type || (row.scan_type && row.scan_type.length > 0)
            hasValueColumns.msn_aqcuisition_type = hasValueColumns.msn_aqcuisition_type || (row.msn_aqcuisition_type && row.msn_aqcuisition_type.length > 0)
            hasValueColumns.fragmentation_method = hasValueColumns.fragmentation_method || (row.fragmentation_method && row.fragmentation_method.length > 0)
            hasValueColumns.data_type = hasValueColumns.data_type || (row.data_type && row.data_type.length > 0)
            hasValueColumns.data_processing_type = hasValueColumns.data_processing_type || (row.data_processing_type && row.data_processing_type.length > 0)
            hasValueColumns.data_processing_method_type = hasValueColumns.data_processing_method_type || (row.data_processing_method_type && row.data_processing_method_type.length > 0)
            hasValueColumns.annotation_type = hasValueColumns.annotation_type || (row.annotation_type && row.annotation_type.length > 0)
            hasValueColumns.data_category = hasValueColumns.data_category || (row.data_category && row.data_category.length > 0)
            hasValueColumns.gc_column_type = hasValueColumns.gc_column_type || (row.gc_column_type && row.gc_column_type.length > 0)
            hasValueColumns.lc_column_type = hasValueColumns.lc_column_type || (row.lc_column_type && row.lc_column_type.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [])
    }, [typeList])

    return hiddenColumns
}

export const useSupplementaryFileHiddenColumns = (supplementaryFileList) => {

    const hiddenColumns = useMemo(() => {
        const hasValueColumns = {}

        if (!supplementaryFileList) return []

        supplementaryFileList.forEach(row => {
            hasValueColumns.file_name = hasValueColumns.file_name || (row.file_name && row.file_name.length > 0)
            hasValueColumns.file_format = hasValueColumns.file_format || (row.file_format && row.file_format.length > 0)
            hasValueColumns.download_url = hasValueColumns.download_url || (row.download_url && row.download_url.length > 0)
            hasValueColumns.md5 = hasValueColumns.md5 || (row.md5 && row.md5.length > 0)
        })

        return Object.entries(hasValueColumns).reduce((hiddenColumns, [key, value]) => value ? hiddenColumns : [...hiddenColumns, key], [
            'local_folder_path',
        ])
    }, [supplementaryFileList])

    return hiddenColumns
}

export const useProjectSummary = (project)=> {
    const isDraft = useIsDraft()
    const intl = useIntl()

    if(isDraft) {
        return {
            title: project && project.draftListdata && project.draftListdata.titles.title
                ? project.draftListdata.titles.title[0]: project.listdata
                && project.listdata.titles.title
                    ? project.listdata.titles.title[0] : "",
            status : project && project.hidden
                ? intl.formatMessage({id: 'project.detail.overview.status.description.private'})
                : project && project.publishedDate ? intl.formatMessage({id: 'project.detail.overview.description.status.publish'}) : intl.formatMessage({id: 'project.detail.overview.description.status.unpublish'}),
            publishedDate: project && project.publishedDate ? project.publishedDate : null,
            experiments: project && project.draftAggregate && project.draftAggregate.experiments ? project.draftAggregate.experiments : project.aggregate.experiments,
            projectFiles: project && project.draftAggregate && project.draftAggregate.projectFiles ? project.draftAggregate.projectFiles : project.aggregate.projectFiles
        }
    }

    return {
        title: project && project.listdata && project.listdata.titles.title ? project.listdata.titles.title[0] : "",
        status : project && project.hidden ? intl.formatMessage({id: 'project.detail.overview.status.description.private'})
            : project && project.publishedDate ? intl.formatMessage({id: 'project.detail.overview.description.status.publish'}) : intl.formatMessage({id: 'project.detail.overview.description.status.unpublish'}),
        publishedDate: project && project.publishedDate ? project.publishedDate : null,
        experiments: project && project.aggregate.experiments ? project.aggregate.experiments : [],
        projectFiles: project && project.aggregate.projectFiles ? project.aggregate.projectFiles : []
    }
}