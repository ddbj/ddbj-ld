import * as getService from '../../get'
import * as getResourceService from '../../get/resource'
import * as value from './value'
import {getResourceId, getSheetRows} from "../../get";
import * as constant from "../../constant";
import {getPerson} from "../../get/resource";

export const parseProject = (projectBook, project) => {
    const projectId = getService.getId(projectBook)

    return {
        id: value.parse(project.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'project',
                resourceId: id
            }
        })),
        title: value.parse(project.title),
        title_ja: value.parse(project.title_ja),
        description: value.parse(project.description),
        description_ja: value.parse(project.description_ja),
        person_creator: value.parse(project.person_creator, personCreator => ({
            content: getPerson(projectBook, personCreator) ? getPerson(projectBook, personCreator).label : null,
            option: {
                projectId,
                resourceName: 'person',
                resourceId: getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personCreator)  ? getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personCreator) : personCreator
            }
        })),
        person_contact: value.parse(project.person_contact, personContact => ({
            content: getPerson(projectBook, personContact) ? getPerson(projectBook, personContact).label : null,
            option: {
                projectId,
                resourceName: 'person',
                resourceId: getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personContact)  ? getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personContact) : personContact
            }
        })),
        person_principal_investigator: value.parse(project.person_principal_investigator, personPrincipalInvestigator => ({
            content: getPerson(projectBook, personPrincipalInvestigator) ? getPerson(projectBook, personPrincipalInvestigator).label : null,
            option: {
                projectId,
                resourceName: 'person',
                resourceId: getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personPrincipalInvestigator)  ? getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personPrincipalInvestigator) : personPrincipalInvestigator
            }
        })),
        person_submitter: value.parse(project.person_submitter, personSubmitter => ({
            content: getPerson(projectBook, personSubmitter) ? getPerson(projectBook, personSubmitter).label : null,
            option: {
                projectId,
                resourceName: 'person',
                resourceId: getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personSubmitter)  ? getResourceId(projectBook, constant.SHEET_ID_PERSON, "label", personSubmitter) : personSubmitter
            }
        })),
        reference: value.parse(project.reference, reference => ({
            content: reference,
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        })),
        funding_source: value.parse(project.funding_source),
        project_related: value.parse(project.project_related, projectRelated => ({
            content: projectRelated,
            option: {
                projectId: projectRelated,
            }
        })),
        comment: value.parse(project.comment),
        comment_ja: value.parse(project.comment_ja)
    }
}

export const parseExperiment = (projectBook, experiment) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(experiment.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'experiment',
                resourceId: id
            }
        })),
        name: value.parse(experiment.name),
        comment: value.parse(experiment.comment),
        description: value.parse(experiment.description),
        measurement_type: value.parse(experiment.measurement_type, measurementType => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', measurementType, {
                labelId: 'type',
                value: 'Measurement Type'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        technology_type: value.parse(experiment.technology_type, technologyType => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', technologyType, {
                labelId: 'type',
                value: 'Technology Type'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        technology_platform: value.parse(experiment.technology_platform, technologyPlatform => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', technologyPlatform, {
                labelId: 'type',
                value: 'Technology Platform'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        date_time: value.parse(experiment.date_time),
        person_experimenter: value.parse(experiment.person_experimenter, personExperimenter => ({
            content: personExperimenter,
            option: {
                projectId,
                resourceName: 'person',
                resourceId: getService.getResourceId(projectBook, 'person', 'id', personExperimenter)
            }
        })),
        experimental_design: value.parse(experiment.experimental_design, experimentalDesign => ({
            content: experimentalDesign,
            option: {
                projectId,
                resourceName: 'experimental_design',
                resourceId: getService.getResourceId(projectBook, 'experimental_design', 'id', experimentalDesign)
            }
        })),
        data_analysis_id: value.parse(experiment.data_analysis_id, dataAnalysisId => ({
            content: dataAnalysisId,
            option: {
                projectId,
                resourceName: 'data_analysis',
                resourceId: getService.getResourceId(projectBook, 'data_analysis', 'id', dataAnalysisId)
            }
        })),
        reference: value.parse(experiment.reference, reference => ({
            content: reference,
            option: {
                projectId,
                resourceName: 'reference',
                resourceId: getService.getResourceId(projectBook, 'reference', 'id', reference)
            }
        }))
    }
}

export const parseExperimentalDesign = (projectBook, experimentalDesign) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(experimentalDesign.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'experimental_design',
                resourceId: id
            }
        })),
        experiment_type: value.parse(experimentalDesign.experiment_type, experimentType => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', experimentType, {
                labelId: 'type',
                value: 'Experiment Type'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        experimental_factor: value.parse(experimentalDesign.experimental_factor, experimentalFactor => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', experimentalFactor, {
                labelId: 'type',
                value: 'Experimental Factor'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        quality_control: value.parse(experimentalDesign.quality_control),
        comment: value.parse(experimentalDesign.comment),
        description: value.parse(experimentalDesign.description)
    }
}

export const parseSamplePrep = (projectBook, samplePrep) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrep.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: id
            }
        })),
        name: value.parse(samplePrep.name),
        category: value.parse(samplePrep.category),
        comment: value.parse(samplePrep.comment)
    }
}

export const parseSamplePrepAnimal = (projectBook, samplePrepAnimal) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepAnimal.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_animal',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepAnimal.name),
        name_ja: value.parse(samplePrepAnimal.name_ja),
        description: value.parse(samplePrepAnimal.description),
        description_ja: value.parse(samplePrepAnimal.description_ja),
        reference: value.parse(samplePrepAnimal.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        })),
        day_length: value.parse(samplePrepAnimal.day_length),
        night_length: value.parse(samplePrepAnimal.night_length),
        humidity: value.parse(samplePrepAnimal.humidity),
        day_temperature: value.parse(samplePrepAnimal.day_temperature),
        night_temperature: value.parse(samplePrepAnimal.night_temperature),
        temperature: value.parse(samplePrepAnimal.temperature),
        light_condition: value.parse(samplePrepAnimal.light_condition, lightCondition => ({
            content: lightCondition,
            option: {
                projectId,
                resourceName: 'light_condition',
                resourceId: getService.getResourceId(projectBook, 'light_condition', 'name', lightCondition)
            }
        })),
        sampling_date: value.parse(samplePrepAnimal.sampling_date),
        sampling_time: value.parse(samplePrepAnimal.sampling_time),
        sampling_location: value.parse(samplePrepAnimal.sampling_location),
        breeding_condition: value.parse(samplePrepAnimal.breeding_condition),
        acclimation_duration: value.parse(samplePrepAnimal.acclimation_duration),
        cage_type: value.parse(samplePrepAnimal.cage_type),
        cage_cleaning_frequency: value.parse(samplePrepAnimal.cage_cleaning_frequency),
        feeding: value.parse(samplePrepAnimal.feeding),
        food_manufacturer: value.parse(samplePrepAnimal.food_manufacturer),
        water_access: value.parse(samplePrepAnimal.water_access),
        water_type: value.parse(samplePrepAnimal.water_type),
        water_quality: value.parse(samplePrepAnimal.water_quality),
        enthanasia_method: value.parse(samplePrepAnimal.enthanasia_method),
        tissue_collection_method: value.parse(samplePrepAnimal.tissue_collection_method),
        tissue_processing_method: value.parse(samplePrepAnimal.tissue_processing_method),
        veterinary_treatment: value.parse(samplePrepAnimal.veterinary_treatment),
        anesthesia: value.parse(samplePrepAnimal.anesthesia),
        metabolism_quenching_method: value.parse(samplePrepAnimal.metabolism_quenching_method),
        sample_storage_method: value.parse(samplePrepAnimal.sample_storage_method)
    }
}

export const parseSamplePrepPlant = (projectBook, samplePrepPlant) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepPlant.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_plant',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepPlant.name),
        name_ja: value.parse(samplePrepPlant.name_ja),
        description: value.parse(samplePrepPlant.description),
        description_ja: value.parse(samplePrepPlant.description_ja),
        reference: value.parse(samplePrepPlant.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        })),
        growth_condition: value.parse(samplePrepPlant.growth_condition),
        day_length: value.parse(samplePrepPlant.day_length),
        night_length: value.parse(samplePrepPlant.night_length),
        humidity: value.parse(samplePrepPlant.humidity),
        day_temperature: value.parse(samplePrepPlant.day_temperature),
        night_temperature: value.parse(samplePrepPlant.night_temperature),
        temperature: value.parse(samplePrepPlant.temperature),
        light_condition: value.parse(samplePrepPlant.light_condition, lightCondition => ({
            content: lightCondition,
            option: {
                projectId,
                resourceName: 'light_condition',
                resourceId: getService.getResourceId(projectBook, 'light_condition', 'name', lightCondition)
            }
        })),
        sampling_data: value.parse(samplePrepPlant.sampling_data),
        sampling_time: value.parse(samplePrepPlant.sampling_time),
        sampling_location: value.parse(samplePrepPlant.sampling_location),
        watering_regime: value.parse(samplePrepPlant.watering_regime),
        nutritional_regime: value.parse(samplePrepPlant.nutritional_regime),
        growth_medium: value.parse(samplePrepPlant.growth_medium),
        growth_location: value.parse(samplePrepPlant.growth_location),
        plot_design: value.parse(samplePrepPlant.plot_design),
        sowing_date: value.parse(samplePrepPlant.sowing_date),
        metabolism_quenching_method: value.parse(samplePrepPlant.metabolism_quenching_method),
        sample_storage_method: value.parse(samplePrepPlant.sample_storage_method)
    }
}

export const parseSamplePrepChemical = (projectBook, samplePrepChemical) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepChemical.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_chemical',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepChemical.name),
        name_ja: value.parse(samplePrepChemical.name_ja),
        description: value.parse(samplePrepChemical.description),
        description_ja: value.parse(samplePrepChemical.description_ja),
        reference: value.parse(samplePrepChemical.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseSamplePrepOther = (projectBook, samplePrepOther) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepOther.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_other',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepOther.name),
        name_ja: value.parse(samplePrepOther.name_ja),
        description: value.parse(samplePrepOther.description),
        description_ja: value.parse(samplePrepOther.description_ja),
        reference: value.parse(samplePrepOther.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseLightCondition = (projectBook, lightCondition) => {
    return {
        name: value.parse(lightCondition.name),
        light_quality: value.parse(lightCondition.light_quality),
        light_intensity: value.parse(lightCondition.light_intensity),
        peak_wave_length: value.parse(lightCondition.peak_wave_length)
    }
}

export const parseSamplePrepClinical = (projectBook, samplePrepClinical) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepClinical.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_clinical',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepClinical.name),
        name_ja: value.parse(samplePrepClinical.name_ja),
        description: value.parse(samplePrepClinical.description),
        description_ja: value.parse(samplePrepClinical.description_ja),
        reference: value.parse(samplePrepClinical.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseSamplePrepBacteria = (projectBook, samplePrepBacteria) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepBacteria.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_bacteria',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepBacteria.name),
        name_ja: value.parse(samplePrepBacteria.name_ja),
        description: value.parse(samplePrepBacteria.description),
        description_ja: value.parse(samplePrepBacteria.description_ja),
        reference: value.parse(samplePrepBacteria.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseSamplePrepEnvironment = (projectBook, samplePrepEnvironment) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepEnvironment.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_environment',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepEnvironment.name),
        name_ja: value.parse(samplePrepEnvironment.name_ja),
        description: value.parse(samplePrepEnvironment.description),
        description_ja: value.parse(samplePrepEnvironment.description_ja),
        reference: value.parse(samplePrepEnvironment.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseSamplePrepFood = (projectBook, samplePrepFood) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepFood.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_food',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepFood.name),
        name_ja: value.parse(samplePrepFood.name_ja),
        description: value.parse(samplePrepFood.description),
        description_ja: value.parse(samplePrepFood.description_ja),
        reference: value.parse(samplePrepFood.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseSamplePrepControl = (projectBook, samplePrepControl) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePrepControl.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_prep_control',
                resourceId: id
            }
        })),
        name: value.parse(samplePrepControl.name),
        name_ja: value.parse(samplePrepControl.name_ja),
        description: value.parse(samplePrepControl.description),
        description_ja: value.parse(samplePrepControl.description_ja),
        reference: value.parse(samplePrepControl.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseSample = (projectBook, sample) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sample.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample',
                resourceId: id
            }
        })),
        name: value.parse(sample.name),
        category: value.parse(sample.category),
        comment: value.parse(sample.comment)
    }
}

export const parseSampleAnimal = (projectBook, sampleAnimal) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleAnimal.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_animal',
                resourceId: id
            }
        })),
        name: value.parse(sampleAnimal.name),
        name_ja: value.parse(sampleAnimal.name_ja),
        sample_preparation_id: value.parse(sampleAnimal.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleAnimal.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleAnimal.comment),
        description: value.parse(sampleAnimal.description),
        description_ja: value.parse(sampleAnimal.description_ja),
        supplier: value.parse(sampleAnimal.supplier),
        supplier_product_id: value.parse(sampleAnimal.supplier_product_id),
        amount_collected: value.parse(sampleAnimal.amount_collected),
        treatment: value.parse(sampleAnimal.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        })),
        bio_sample_id: value.parse(sampleAnimal.bio_sample_id),
        species_name: value.parse(sampleAnimal.species_name),
        species_name_ja: value.parse(sampleAnimal.species_name_ja),
        taxonomy_ncbi: value.parse(sampleAnimal.taxonomy_ncbi),
        cultivar: value.parse(sampleAnimal.cultivar),
        cultivar_ja: value.parse(sampleAnimal.cultivar_ja),
        genotype: value.parse(sampleAnimal.genotype),
        biomaterial_organ: value.parse(sampleAnimal.biomaterial_organ, biomaterialOrgan => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', biomaterialOrgan, {
                labelId: 'type',
                value: 'Biol Organ'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        biomaterial_tissue: value.parse(sampleAnimal.biomaterial_tissue, biomaterialTissue => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', biomaterialTissue, {
                labelId: 'type',
                value: 'Biol Tissue'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        mutant: value.parse(sampleAnimal.mutant),
        transgenic_line: value.parse(sampleAnimal.transgenic_line),
        related_gene: value.parse(sampleAnimal.related_gene),
        biomaterial_line: value.parse(sampleAnimal.biomaterial_line, biomaterialLine => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', biomaterialLine, {
                labelId: 'type',
                value: 'Biomaterial Line'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        phenotype: value.parse(sampleAnimal.phenotype, phenotype => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', phenotype, {
                labelId: 'type',
                value: 'Phenotype and Trait'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        phenotypic_sex: value.parse(sampleAnimal.phenotypic_sex, phenotypicSex => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', phenotypicSex, {
                labelId: 'type',
                value: 'Phenotype and Trait'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        disease: value.parse(sampleAnimal.disease),
        clinical_signs: value.parse(sampleAnimal.clinical_signs)
    }
}

export const parseSamplePlant = (projectBook, samplePlant) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(samplePlant.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_plant',
                resourceId: id
            }
        })),
        name: value.parse(samplePlant.name),
        name_ja: value.parse(samplePlant.name_ja),
        sample_preparation_id: value.parse(samplePlant.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(samplePlant.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(samplePlant.comment),
        description: value.parse(samplePlant.description),
        description_ja: value.parse(samplePlant.description_ja),
        supplier: value.parse(samplePlant.supplier),
        supplier_product_id: value.parse(samplePlant.supplier_product_id),
        amount_collected: value.parse(samplePlant.amount_collected),
        treatment: value.parse(samplePlant.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        })),
        bio_sample_id: value.parse(samplePlant.bio_sample_id),
        species_name: value.parse(samplePlant.species_name),
        species_name_ja: value.parse(samplePlant.species_name_ja),
        taxonomy_ncbi: value.parse(samplePlant.taxonomy_ncbi),
        cultivar: value.parse(samplePlant.cultivar),
        cultivar_ja: value.parse(samplePlant.cultivar_ja),
        genotype: value.parse(samplePlant.genotype),
        developmental_stage: value.parse(samplePlant.developmental_stage),
        biomaterial_organ: value.parse(samplePlant.biomaterial_organ, biomaterialOrgan => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', biomaterialOrgan, {
                labelId: 'type',
                value: 'Biol Organ'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        biomaterial_tissue: value.parse(samplePlant.biomaterial_tissue, biomaterialTissue => {
            const term = getService.getSheetRowWithKeyAndValue(projectBook, 'term', 'name', biomaterialTissue, {
                labelId: 'type',
                value: 'Biol Tissue'
            })
            if (!term) return {content: "not found target data", option: {}}

            return {
                content: term.name[0],
                option: {
                    href: term.ontology_uri ? term.ontology_uri[0] : null
                }
            }
        }),
        mutant: value.parse(samplePlant.mutant),
        transgenic_line: value.parse(samplePlant.transgenic_line),
        related_gene: value.parse(samplePlant.related_gene)
    }
}

export const parseSampleChemical = (projectBook, sampleChemical) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleChemical.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_chemical',
                resourceId: id
            }
        })),
        name: value.parse(sampleChemical.name),
        name_ja: value.parse(sampleChemical.name_ja),
        sample_preparation_id: value.parse(sampleChemical.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleChemical.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleChemical.comment),
        description: value.parse(sampleChemical.description),
        description_ja: value.parse(sampleChemical.description_ja),
        supplier: value.parse(sampleChemical.supplier),
        supplier_product_id: value.parse(sampleChemical.supplier_product_id),
        amount_collected: value.parse(sampleChemical.amount_collected),
        treatment: value.parse(sampleChemical.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        })),
        chemical_formula: value.parse(sampleChemical.chemical_formula),
        smiles: value.parse(sampleChemical.smiles),
        inchi: value.parse(sampleChemical.inchi),
        inchi_key: value.parse(sampleChemical.inchi_key)
    }
}

export const parseSampleOther = (projectBook, sampleOther) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleOther.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_other',
                resourceId: id
            }
        })),
        name: value.parse(sampleOther.name),
        name_ja: value.parse(sampleOther.name_ja),
        sample_preparation_id: value.parse(sampleOther.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleOther.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleOther.comment),
        description: value.parse(sampleOther.description),
        description_ja: value.parse(sampleOther.description_ja),
        supplier: value.parse(sampleOther.supplier),
        supplier_product_id: value.parse(sampleOther.supplier_product_id),
        amount_collected: value.parse(sampleOther.amount_collected),
        treatment: value.parse(sampleOther.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        }))
    }
}

export const parseSampleClinical = (projectBook, sampleClinical) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleClinical.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_clinical',
                resourceId: id
            }
        })),
        name: value.parse(sampleClinical.name),
        name_ja: value.parse(sampleClinical.name_ja),
        sample_preparation_id: value.parse(sampleClinical.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleClinical.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleClinical.comment),
        description: value.parse(sampleClinical.description),
        description_ja: value.parse(sampleClinical.description_ja),
        supplier: value.parse(sampleClinical.supplier),
        supplier_product_id: value.parse(sampleClinical.supplier_product_id),
        amount_collected: value.parse(sampleClinical.amount_collected),
        treatment: value.parse(sampleClinical.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        }))
    }
}

export const parseSampleBacteria = (projectBook, sampleBacteria) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleBacteria.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_bacteria',
                resourceId: id
            }
        })),
        name: value.parse(sampleBacteria.name),
        name_ja: value.parse(sampleBacteria.name_ja),
        sample_preparation_id: value.parse(sampleBacteria.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleBacteria.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleBacteria.comment),
        description: value.parse(sampleBacteria.description),
        description_ja: value.parse(sampleBacteria.description_ja),
        supplier: value.parse(sampleBacteria.supplier),
        supplier_product_id: value.parse(sampleBacteria.supplier_product_id),
        amount_collected: value.parse(sampleBacteria.amount_collected),
        treatment: value.parse(sampleBacteria.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        }))
    }
}

export const parseSampleEnvironment = (projectBook, sampleEnvironment) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleEnvironment.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_environment',
                resourceId: id
            }
        })),
        name: value.parse(sampleEnvironment.name),
        name_ja: value.parse(sampleEnvironment.name_ja),
        sample_preparation_id: value.parse(sampleEnvironment.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleEnvironment.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleEnvironment.comment),
        description: value.parse(sampleEnvironment.description),
        description_ja: value.parse(sampleEnvironment.description_ja),
        supplier: value.parse(sampleEnvironment.supplier),
        supplier_product_id: value.parse(sampleEnvironment.supplier_product_id),
        amount_collected: value.parse(sampleEnvironment.amount_collected),
        treatment: value.parse(sampleEnvironment.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        }))
    }
}

export const parseSampleFood = (projectBook, sampleFood) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleFood.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_food',
                resourceId: id
            }
        })),
        name: value.parse(sampleFood.name),
        name_ja: value.parse(sampleFood.name_ja),
        sample_preparation_id: value.parse(sampleFood.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleFood.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleFood.comment),
        description: value.parse(sampleFood.description),
        description_ja: value.parse(sampleFood.description_ja),
        supplier: value.parse(sampleFood.supplier),
        supplier_product_id: value.parse(sampleFood.supplier_product_id),
        amount_collected: value.parse(sampleFood.amount_collected),
        treatment: value.parse(sampleFood.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        }))
    }
}

export const parseSampleControl = (projectBook, sampleControl) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(sampleControl.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'sample_control',
                resourceId: id
            }
        })),
        name: value.parse(sampleControl.name),
        name_ja: value.parse(sampleControl.name_ja),
        sample_preparation_id: value.parse(sampleControl.sample_preparation_id, samplePreparationId => ({
            content: samplePreparationId,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'id', samplePreparationId)
            }
        })),
        sample_preparation_name: value.parse(sampleControl.sample_preparation_name, samplePreparationName => ({
            content: samplePreparationName,
            option: {
                projectId,
                resourceName: 'sample_prep',
                resourceId: getService.getResourceId(projectBook, 'sample_prep', 'name', samplePreparationName)
            }
        })),
        comment: value.parse(sampleControl.comment),
        description: value.parse(sampleControl.description),
        description_ja: value.parse(sampleControl.description_ja),
        supplier: value.parse(sampleControl.supplier),
        supplier_product_id: value.parse(sampleControl.supplier_product_id),
        amount_collected: value.parse(sampleControl.amount_collected),
        treatment: value.parse(sampleControl.treatment, treatment => ({
            content: treatment,
            option: {
                projectId,
                resourceName: 'treatment',
                resourceId: getService.getResourceId(projectBook, 'treatment', 'name', treatment)
            }
        }))
    }
}

export const parseTreatment = (projectBook, treatment) => {
    return {
        name: value.parse(treatment.name),
        comment: value.parse(treatment.comment),
        description: value.parse(treatment.description),
        concentration: value.parse(treatment.concentration)
    }
}

export const parseExtractionMethod = (projectBook, extractionMethod) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(extractionMethod.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'extraction_method',
                resourceId: id
            }
        })),
        name: value.parse(extractionMethod.name),
        name_ja: value.parse(extractionMethod.name_ja),
        comment: value.parse(extractionMethod.comment),
        description: value.parse(extractionMethod.description),
        internal_standard: value.parse(extractionMethod.internal_standard),
        derivatisation: value.parse(extractionMethod.derivatisation),
        extract_concentration: value.parse(extractionMethod.extract_concentration),
        extract_storage_method: value.parse(extractionMethod.extract_storage_method),
        reference: value.parse(extractionMethod.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseMeasurementMethod = (projectBook, measurementMethod) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurementMethod.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement_method',
                resourceId: id
            }
        })),
        name: value.parse(measurementMethod.name),
        category: value.parse(measurementMethod.category),
        comment: value.parse(measurementMethod.comment)
    }
}

export const parseMeasurementMethodLcms = (projectBook, measurementMethodLcms) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurementMethodLcms.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement_method_lcms',
                resourceId: id
            }
        })),
        name: value.parse(measurementMethodLcms.name),
        description: value.parse(measurementMethodLcms.description),
        description_ja: value.parse(measurementMethodLcms.description_ja),
        lc_condition_name: value.parse(measurementMethodLcms.lc_condition_name, lcConditionName => ({
            content: lcConditionName,
            option: {
                projectId,
                resourceName: 'condition_lc',
                resourceId: getService.getResourceId(projectBook, 'condition_lc', 'name', lcConditionName)
            }
        })),
        ms_condition_name: value.parse(measurementMethodLcms.ms_condition_name, msConditionName => ({
            content: msConditionName,
            option: {
                projectId,
                resourceName: 'condition_ms',
                resourceId: getService.getResourceId(projectBook, 'condition_ms', 'name', msConditionName)
            }
        })),
        other_condition_name: value.parse(measurementMethodLcms.other_condition_name, otherConditionName => ({
            content: otherConditionName,
            option: {
                projectId,
                resourceName: 'condition_other',
                resourceId: getService.getResourceId(projectBook, 'condition_other', 'name', otherConditionName)
            }
        })),
        reference: value.parse(measurementMethodLcms.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseMeasurementMethodGcms = (projectBook, measurementMethodGcms) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurementMethodGcms.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement_method_gcms',
                resourceId: id
            }
        })),
        name: value.parse(measurementMethodGcms.name),
        description: value.parse(measurementMethodGcms.description),
        description_ja: value.parse(measurementMethodGcms.description_ja),
        gc_condition_name: value.parse(measurementMethodGcms.gc_condition_name, gcConditionName => ({
            content: gcConditionName,
            option: {
                projectId,
                resourceName: 'condition_gc',
                resourceId: getService.getResourceId(projectBook, 'condition_gc', 'name', gcConditionName)
            }
        })),
        ms_condition_name: value.parse(measurementMethodGcms.ms_condition_name, msConditionName => ({
            content: msConditionName,
            option: {
                projectId,
                resourceName: 'condition_ms',
                resourceId: getService.getResourceId(projectBook, 'condition_ms', 'name', msConditionName)
            }
        })),
        other_condition_name: value.parse(measurementMethodGcms.other_condition_name, otherConditionName => ({
            content: otherConditionName,
            option: {
                projectId,
                resourceName: 'condition_other',
                resourceId: getService.getResourceId(projectBook, 'condition_other', 'name', otherConditionName)
            }
        })),
        reference: value.parse(measurementMethodGcms.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseMeasurementMethodCems = (projectBook, measurementMethodCems) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurementMethodCems.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement_method_cems',
                resourceId: id
            }
        })),
        name: value.parse(measurementMethodCems.name),
        description: value.parse(measurementMethodCems.description),
        description_ja: value.parse(measurementMethodCems.description_ja),
        ce_condition_name: value.parse(measurementMethodCems.ce_condition_name, ceConditionName => ({
            content: ceConditionName,
            option: {
                projectId,
                resourceName: 'condition_ce',
                resourceId: getService.getResourceId(projectBook, 'condition_ce', 'name', ceConditionName)
            }
        })),
        ms_condition_name: value.parse(measurementMethodCems.ms_condition_name, msConditionName => ({
            content: msConditionName,
            option: {
                projectId,
                resourceName: 'condition_ms',
                resourceId: getService.getResourceId(projectBook, 'condition_ms', 'name', msConditionName)
            }
        })),
        other_condition_name: value.parse(measurementMethodCems.other_condition_name, otherConditionName => ({
            content: otherConditionName,
            option: {
                projectId,
                resourceName: 'condition_other',
                resourceId: getService.getResourceId(projectBook, 'condition_other', 'name', otherConditionName)
            }
        })),
        reference: value.parse(measurementMethodCems.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseMeasurementMethodMs = (projectBook, measurementMethodMs) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurementMethodMs.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement_method_ms',
                resourceId: id
            }
        })),
        name: value.parse(measurementMethodMs.name),
        description: value.parse(measurementMethodMs.description),
        description_ja: value.parse(measurementMethodMs.description_ja),
        ms_condition_name: value.parse(measurementMethodMs.ms_condition_name, msConditionName => ({
            content: msConditionName,
            option: {
                projectId,
                resourceName: 'condition_ms',
                resourceId: getService.getResourceId(projectBook, 'condition_ms', 'name', msConditionName)
            }
        })),
        other_condition_name: value.parse(measurementMethodMs.other_condition_name, otherConditionName => ({
            content: otherConditionName,
            option: {
                projectId,
                resourceName: 'condition_other',
                resourceId: getService.getResourceId(projectBook, 'condition_other', 'name', otherConditionName)
            }
        })),
        reference: value.parse(measurementMethodMs.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseMeasurementMethodNmr = (projectBook, measurementMethodNmr) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurementMethodNmr.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement_method_nmr',
                resourceId: id
            }
        })),
        name: value.parse(measurementMethodNmr.name),
        description: value.parse(measurementMethodNmr.description),
        description_ja: value.parse(measurementMethodNmr.description_ja),
        nmr_condition_name: value.parse(measurementMethodNmr.nmr_condition_name, nmrConditionName => ({
            content: nmrConditionName,
            option: {
                projectId,
                resourceName: 'condition_nmr',
                resourceId: getService.getResourceId(projectBook, 'condition_nmr', 'name', nmrConditionName)
            }
        })),
        other_condition_name: value.parse(measurementMethodNmr.other_condition_name, otherConditionName => ({
            content: otherConditionName,
            option: {
                projectId,
                resourceName: 'condition_other',
                resourceId: getService.getResourceId(projectBook, 'condition_other', 'name', otherConditionName)
            }
        })),
        reference: value.parse(measurementMethodNmr.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseMeasurementMethodOther = (projectBook, measurementMethodOther) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurementMethodOther.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement_method_other',
                resourceId: id
            }
        })),
        name: value.parse(measurementMethodOther.name),
        description: value.parse(measurementMethodOther.description),
        description_ja: value.parse(measurementMethodOther.description_ja),
        other_condition_name: value.parse(measurementMethodOther.other_condition_name, otherConditionName => ({
            content: otherConditionName,
            option: {
                projectId,
                resourceName: 'condition_other',
                resourceId: getService.getResourceId(projectBook, 'condition_other', 'name', otherConditionName)
            }
        })),
        reference: value.parse(measurementMethodOther.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseConditionLc = (projectBook, conditionLc) => {
    const projectId = getService.getId(projectBook)
    return {
        name: value.parse(conditionLc.name),
        description: value.parse(conditionLc.description),
        description_ja: value.parse(conditionLc.description_ja),
        instrument: value.parse(conditionLc.instrument, instrument => ({
            content: instrument,
            option: {
                projectId,
                resourceName: 'instrument',
                resourceId: getService.getResourceId(projectBook, 'instrument', 'name', instrument, {
                    labelId: 'type',
                    value: 'LC'
                })
            }
        })),
        column: value.parse(conditionLc.column, column => ({
            content: column,
            option: {
                projectId,
                resourceName: 'column',
                resourceId: getService.getResourceId(projectBook, 'column', 'name', column)
            }
        })),
        column_type: value.parse(conditionLc.column_type),
        column_other: value.parse(conditionLc.column_other),
        column_temperature: value.parse(conditionLc.column_temperature),
        column_pressure: value.parse(conditionLc.column_pressure),
        solvent_a: value.parse(conditionLc.solvent_a),
        solvent_b: value.parse(conditionLc.solvent_b),
        solvent_other: value.parse(conditionLc.solvent_other),
        flow_gradient: value.parse(conditionLc.flow_gradient),
        flow_rate: value.parse(conditionLc.flow_rate),
        elution_detector: value.parse(conditionLc.elution_detector),
        elution_detector_wave_length_min: value.parse(conditionLc.elution_detector_wave_length_min),
        elution_detector_wave_length_max: value.parse(conditionLc.elution_detector_wave_length_max),
        analytical_time: value.parse(conditionLc.analytical_time),
        control_software: value.parse(conditionLc.control_software, controlSoftware => ({
            content: controlSoftware,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', controlSoftware)
            }
        }))
    }
}

export const parseConditionGc = (projectBook, conditionGc) => {
    const projectId = getService.getId(projectBook)
    return {
        name: value.parse(conditionGc.name),
        description: value.parse(conditionGc.description),
        description_ja: value.parse(conditionGc.description_ja),
        instrument: value.parse(conditionGc.instrument, instrument => ({
            content: instrument,
            option: {
                projectId,
                resourceName: 'instrument',
                resourceId: getService.getResourceId(projectBook, 'instrument', 'name', instrument, {
                    labelId: 'type',
                    value: 'GC'
                })
            }
        })),
        column: value.parse(conditionGc.column, column => ({
            content: column,
            option: {
                projectId,
                resourceName: 'column',
                resourceId: getService.getResourceId(projectBook, 'column', 'name', column)
            }
        })),
        column_type: value.parse(conditionGc.column_type),
        column_other: value.parse(conditionGc.column_other),
        column_temperature: value.parse(conditionGc.column_temperature),
        column_pressure: value.parse(conditionGc.column_pressure),
        temperature_gradient: value.parse(conditionGc.temperature_gradient),
        flow_gradient: value.parse(conditionGc.flow_gradient),
        flow_rate: value.parse(conditionGc.flow_rate),
        flow_gas: value.parse(conditionGc.flow_gas),
        analytical_time: value.parse(conditionGc.analytical_time),
        control_software: value.parse(conditionGc.control_software, controlSoftware => ({
            content: controlSoftware,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', controlSoftware)
            }
        }))
    }
}

export const parseConditionCe = (projectBook, conditionCe) => {
    const projectId = getService.getId(projectBook)
    return {
        name: value.parse(conditionCe.name),
        description: value.parse(conditionCe.description),
        description_ja: value.parse(conditionCe.description_ja),
        instrument: value.parse(conditionCe.instrument, instrument => ({
            content: instrument,
            option: {
                projectId,
                resourceName: 'instrument',
                resourceId: getService.getResourceId(projectBook, 'instrument', 'name', instrument, {
                    labelId: 'type',
                    value: 'CE'
                })
            }
        })),
        column: value.parse(conditionCe.column, column => ({
            content: column,
            option: {
                projectId,
                resourceName: 'column',
                resourceId: getService.getResourceId(projectBook, 'column', 'name', column)
            }
        })),
        column_type: value.parse(conditionCe.column_type),
        column_other: value.parse(conditionCe.column_other),
        column_temperature: value.parse(conditionCe.column_temperature),
        column_pressure: value.parse(conditionCe.column_pressure),
        solvent: value.parse(conditionCe.solvent),
        flow_gradient: value.parse(conditionCe.flow_gradient),
        flow_rate: value.parse(conditionCe.flow_rate),
        elution_detector: value.parse(conditionCe.elution_detector),
        elution_detector_wave_length_min: value.parse(conditionCe.elution_detector_wave_length_min),
        elution_detector_wave_length_max: value.parse(conditionCe.elution_detector_wave_length_max),
        analytical_time: value.parse(conditionCe.analytical_time),
        control_software: value.parse(conditionCe.control_software, controlSoftware => ({
            content: controlSoftware,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', controlSoftware)
            }
        }))
    }
}

export const parseConditionMs = (projectBook, conditionMs) => {
    const projectId = getService.getId(projectBook)
    return {
        name: value.parse(conditionMs.name),
        description: value.parse(conditionMs.description),
        description_ja: value.parse(conditionMs.description_ja),
        instrument: value.parse(conditionMs.instrument, instrument => ({
            content: instrument,
            option: {
                projectId,
                resourceName: 'instrument',
                resourceId: getService.getResourceId(projectBook, 'instrument', 'name', instrument, {
                    labelId: 'type',
                    value: 'MS'
                })
            }
        })),
        ms_instrument_type: value.parse(conditionMs.ms_instrument_type),
        ion_source: value.parse(conditionMs.ion_source),
        ionization_polarity: value.parse(conditionMs.ionization_polarity),
        ionization_energy: value.parse(conditionMs.ionization_energy),
        scan_type: value.parse(conditionMs.scan_type),
        full_scan_mz_range: value.parse(conditionMs.full_scan_mz_range),
        ms_acquisition_rate: value.parse(conditionMs.ms_acquisition_rate),
        msn_acquisition_method: value.parse(conditionMs.msn_acquisition_method),
        scan_program_details: value.parse(conditionMs.scan_program_details),
        resolution: value.parse(conditionMs.resolution),
        mz_accuracy_full_scan: value.parse(conditionMs.mz_accuracy_full_scan),
        mz_accuracy_msn_scan: value.parse(conditionMs.mz_accuracy_msn_scan),
        capillary_temperature: value.parse(conditionMs.capillary_temperature),
        collision_energy: value.parse(conditionMs.collision_energy),
        ion_source_temperature: value.parse(conditionMs.ion_source_temperature),
        ion_spray_voltage: value.parse(conditionMs.ion_spray_voltage),
        fragmentation_method: value.parse(conditionMs.fragmentation_method),
        retention_index_method: value.parse(conditionMs.retention_index_method),
        desolvation_temperature: value.parse(conditionMs.desolvation_temperature),
        sheath_gas: value.parse(conditionMs.sheath_gas),
        control_software: value.parse(conditionMs.control_software, controlSoftware => ({
            content: controlSoftware,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', controlSoftware)
            }
        }))
    }
}

export const parseConditionNmr = (projectBook, conditionNmr) => {
    const projectId = getService.getId(projectBook)
    return {
        name: value.parse(conditionNmr.name),
        description: value.parse(conditionNmr.description),
        description_ja: value.parse(conditionNmr.description_ja),
        instrument: value.parse(conditionNmr.instrument, instrument => ({
            content: instrument,
            option: {
                projectId,
                resourceName: 'instrument',
                resourceId: getService.getResourceId(projectBook, 'instrument', 'name', instrument)
            }
        })),
        control_software: value.parse(conditionNmr.control_software, controlSoftware => ({
            content: controlSoftware,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', controlSoftware)
            }
        }))
    }
}

export const parseConditionOther = (projectBook, conditionOther) => {
    const projectId = getService.getId(projectBook)
    return {
        name: value.parse(conditionOther.name),
        description: value.parse(conditionOther.description),
        description_ja: value.parse(conditionOther.description_ja),
        instrument: value.parse(conditionOther.instrument, instrument => ({
            content: instrument,
            option: {
                projectId,
                resourceName: 'instrument',
                resourceId: getService.getResourceId(projectBook, 'instrument', 'name', instrument)
            }
        })),
        control_software: value.parse(conditionOther.control_software, controlSoftware => ({
            content: controlSoftware,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', controlSoftware)
            }
        }))
    }
}

export const parseInstrument = (projectBook, instrument) => {
    return {
        name: value.parse(instrument.name),
        type: value.parse(instrument.type),
        supplier: value.parse(instrument.supplier),
        product_name: value.parse(instrument.product_name),
        product_id: value.parse(instrument.product_id),
        comment: value.parse(instrument.comment),
        description: value.parse(instrument.description)
    }
}

export const parseColumn = (projectBook, column) => {
    return {
        name: value.parse(column.name),
        type: value.parse(column.type),
        supplier: value.parse(column.supplier),
        product_name: value.parse(column.product_name),
        product_id: value.parse(column.product_id),
        comment: value.parse(column.comment),
        description: value.parse(column.description)
    }
}

export const parseMeasurement = (projectBook, measurement) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(measurement.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'measurement',
                resourceId: id
            }
        })),
        experiment_id: value.parse(measurement.experiment_id, experimentId => ({
            content: experimentId,
            option: {
                projectId,
                resourceName: 'experiment',
                resourceId: getService.getResourceId(projectBook, 'experiment', 'id', experimentId)
            }
        })),
        experiment_name: value.parse(measurement.experiment_name, experimentName => ({
            content: experimentName,
            option: {
                projectId,
                resourceName: 'experiment',
                resourceId: getService.getResourceId(projectBook, 'experiment', 'name', experimentName)
            }
        })),
        sample_id: value.parse(measurement.sample_id, sampleId => ({
            content: sampleId,
            option: {
                projectId,
                resourceName: 'sample',
                resourceId: getService.getResourceId(projectBook, 'sample', 'id', sampleId)
            }
        })),
        sample_name: value.parse(measurement.sample_name, sampleName => ({
            content: sampleName,
            option: {
                projectId,
                resourceName: 'sample',
                resourceId: getService.getResourceId(projectBook, 'sample', 'name', sampleName)
            }
        })),
        extract_id: value.parse(measurement.extract_id),
        extraction_method_id: value.parse(measurement.extraction_method_id, extractionMethodId => ({
            content: extractionMethodId,
            option: {
                projectId,
                resourceName: 'extraction_method',
                resourceId: getService.getResourceId(projectBook, 'extraction_method', 'id', extractionMethodId)
            }
        })),
        extraction_method_name: value.parse(measurement.extraction_method_name, extractionMethodName => ({
            content: extractionMethodName,
            option: {
                projectId,
                resourceName: 'extraction_method',
                resourceId: getService.getResourceId(projectBook, 'extraction_method', 'name', extractionMethodName)
            }
        })),
        measurement_id: value.parse(measurement.measurement_id),
        measurement_method_id: value.parse(measurement.measurement_method_id, measurementMethodId => ({
            content: measurementMethodId,
            option: {
                projectId,
                resourceName: 'measurement_method',
                resourceId: getService.getResourceId(projectBook, 'measurement_method', 'id', measurementMethodId)
            }
        })),
        measurement_method_name: value.parse(measurement.measurement_method_name, measurementMethodName => ({
            content: measurementMethodName,
            option: {
                projectId,
                resourceName: 'measurement_method',
                resourceId: getService.getResourceId(projectBook, 'measurement_method', 'name', measurementMethodName)
            }
        })),
        date_time: value.parse(measurement.date_time),
        person_experimenter: value.parse(measurement.person_experimenter, personExperimenter => ({
            content: personExperimenter,
            option: {
                projectId,
                resourceName: 'null',
                resourceId: getService.getResourceId(projectBook, 'null', 'null', personExperimenter)
            }
        })),
        comment: value.parse(measurement.comment),
        description: value.parse(measurement.description)
    }
}

export const parseRawDataFile = (projectBook, rawDataFile) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(rawDataFile.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'raw_data_file',
                resourceId: id
            }
        })),
        measurement_id: value.parse(rawDataFile.measurement_id, measurementId => ({
            content: measurementId,
            option: {
                projectId,
                resourceName: 'measurement',
                resourceId: getService.getResourceId(projectBook, 'measurement', 'id', measurementId)
            }
        })),
        data_type: value.parse(rawDataFile.data_type_id),
        name: value.parse(rawDataFile.name),
        comment: value.parse(rawDataFile.comment),
        description: value.parse(rawDataFile.description),
        file_name: value.parse(rawDataFile.file_name),
        file_format: value.parse(rawDataFile.file_format),
        download_url: value.parse(rawDataFile.download_url, downloadUrl => ({
            content: downloadUrl,
            option: {
                href: downloadUrl
            }
        })),
        md5: value.parse(rawDataFile.md5)
    }
}

export const parseDataAnalysis = (projectBook, dataAnalysis) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(dataAnalysis.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'data_analysis',
                resourceId: id
            }
        })),
        name: value.parse(dataAnalysis.name),
        comment: value.parse(dataAnalysis.comment),
        description: value.parse(dataAnalysis.description),
        data_preprocessing: value.parse(dataAnalysis.data_preprocessing, dataPreprocessing => ({
            content: dataPreprocessing,
            option: {
                projectId,
                resourceName: 'data_preprocessing',
                resourceId: getService.getResourceId(projectBook, 'data_preprocessing', 'name', dataPreprocessing)
            }
        })),
        statictical_analysis: value.parse(dataAnalysis.statictical_analysis, staticticalAnalysis => ({
            content: staticticalAnalysis,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', staticticalAnalysis, {
                    labelId: 'type',
                    value: 'Statistical Data Analysis'
                })
            }
        })),
        univariate_analysis: value.parse(dataAnalysis.univariate_analysis, univariateAnalysis => ({
            content: univariateAnalysis,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', univariateAnalysis, {
                    labelId: 'type',
                    value: 'Univariate Analysis'
                })
            }
        })),
        multivariate_analysis: value.parse(dataAnalysis.multivariate_analysis, multivariateAnalysis => ({
            content: multivariateAnalysis,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', multivariateAnalysis, {
                    labelId: 'type',
                    value: 'Multivariate Analysis'
                })
            }
        })),
        visualisation: value.parse(dataAnalysis.visualisation, visualisation => ({
            content: visualisation,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', visualisation, {
                    labelId: 'type',
                    value: 'Visualisation'
                })
            }
        })),
        annotation_method: value.parse(dataAnalysis.annotation_method, annotationMethod => ({
            content: annotationMethod,
            option: {
                projectId,
                resourceName: 'annotation_method',
                resourceId: getService.getResourceId(projectBook, 'annotation_method', 'name', annotationMethod)
            }
        })),
        recommended_decimal_place_mz: value.parse(dataAnalysis.recommended_decimal_place_mz)
    }
}

export const parseAnalyzedRawFile = (projectBook, analyzedRawFile) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(analyzedRawFile.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'analyzed_raw_file',
                resourceId: id
            }
        })),
        data_analysis_id: value.parse(analyzedRawFile.data_analysis_id, dataAnalysisId => ({
            content: dataAnalysisId,
            option: {
                projectId,
                resourceName: 'data_analysis',
                resourceId: getService.getResourceId(projectBook, 'data_analysis', 'id', dataAnalysisId)
            }
        })),
        name: value.parse(analyzedRawFile.name, name => ({
            content: name,
            option: {
                projectId,
                resourceName: 'data_analysis',
                resourceId: getService.getResourceId(projectBook, 'data_analysis', 'name', name)
            }
        })),
        project_id: value.parse(analyzedRawFile.project_id, projectId => ({
            content: projectId,
            option: {
                projectId: projectId,
            }
        })),
        category: value.parse(analyzedRawFile.category)
    }
}

export const parseDataPreprocessing = (projectBook, dataPreprocessing) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(dataPreprocessing.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'data_preprocessing',
                resourceId: id
            }
        })),
        name: value.parse(dataPreprocessing.name),
        comment: value.parse(dataPreprocessing.comment),
        description: value.parse(dataPreprocessing.description),
        general: value.parse(dataPreprocessing.general, general => ({
            content: general,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', general, {
                    labelId: 'type',
                    value: 'General'
                })
            }
        })),
        peak_detection: value.parse(dataPreprocessing.peak_detection, peakDetection => ({
            content: peakDetection,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', peakDetection, {
                    labelId: 'type',
                    value: 'Peak Detection'
                })
            }
        })),
        peak_alignment: value.parse(dataPreprocessing.peak_alignment, peakAlignment => ({
            content: peakAlignment,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', peakAlignment, {
                    labelId: 'type',
                    value: 'Peak Alignment'
                })
            }
        })),
        spectral_extraction: value.parse(dataPreprocessing.spectral_extraction, spectralExtraction => ({
            content: spectralExtraction,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', spectralExtraction, {
                    labelId: 'type',
                    value: 'Spectra Extraction'
                })
            }
        })),
        retention_time_correction: value.parse(dataPreprocessing.retention_time_correction, retentionTimeCorrection => ({
            content: retentionTimeCorrection,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', retentionTimeCorrection, {
                    labelId: 'type',
                    value: 'Retention Time Correction'
                })
            }
        })),
        summarisation: value.parse(dataPreprocessing.summarisation, summarisation => ({
            content: summarisation,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', summarisation, {
                    labelId: 'type',
                    value: 'Summarisation'
                })
            }
        })),
        normalisation: value.parse(dataPreprocessing.normalisation, normalisation => ({
            content: normalisation,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', normalisation, {
                    labelId: 'type',
                    value: 'Normalisation'
                })
            }
        })),
        data_transformation: value.parse(dataPreprocessing.data_transformation, dataTransformation => ({
            content: dataTransformation,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', dataTransformation, {
                    labelId: 'type',
                    value: 'Data Transformation'
                })
            }
        })),
        scaling: value.parse(dataPreprocessing.scaling, scaling => ({
            content: scaling,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: getService.getResourceId(projectBook, 'data_processing_method', 'name', scaling, {
                    labelId: 'type',
                    value: 'Scaling'
                })
            }
        })),
        reference: value.parse(dataPreprocessing.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseDataProcessingMethod = (projectBook, dataProcessingMethod) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(dataProcessingMethod.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'data_processing_method',
                resourceId: id
            }
        })),
        name: value.parse(dataProcessingMethod.name),
        type: value.parse(dataProcessingMethod.type),
        method_type: value.parse(dataProcessingMethod.method_type, methodType => {
            const dataProcessingMethodType = getService.getSheetRowWithKeyAndValue(projectBook, 'data_processing_method_type', 'name', methodType)
            if (!dataProcessingMethodType) return {content: "not found target data", option: {}}

            return {
                content: dataProcessingMethodType.name[0],
                option: {
                    href: dataProcessingMethodType.ontology_uri ? dataProcessingMethodType.ontology_uri[0] : null
                }
            }
        }),
        comment: value.parse(dataProcessingMethod.comment),
        description: value.parse(dataProcessingMethod.description),
        software: value.parse(dataProcessingMethod.software, software => ({
            content: software,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', software)
            }
        })),
        parameter: value.parse(dataProcessingMethod.parameter),
        reference: value.parse(dataProcessingMethod.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseDataProcessingMethodType = (projectBook, dataProcessingMethodType) => {
    return {
        name: value.parse(dataProcessingMethodType.name),
        type: value.parse(dataProcessingMethodType.type),
        ontology_uri: value.parse(dataProcessingMethodType.ontology_uri),
        comment: value.parse(dataProcessingMethodType.comment),
        description: value.parse(dataProcessingMethodType.description)
    }
}

export const parseAnnotationMethod = (projectBook, annotationMethod) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(annotationMethod.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'annotation_method',
                resourceId: id
            }
        })),
        name: value.parse(annotationMethod.name),
        comment: value.parse(annotationMethod.comment),
        description: value.parse(annotationMethod.description),
        software: value.parse(annotationMethod.software, software => ({
            content: software,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: getService.getResourceId(projectBook, 'software', 'name', software)
            }
        })),
        parameters: value.parse(annotationMethod.parameters),
        spectral_library: value.parse(annotationMethod.spectral_library),
        evidence: value.parse(annotationMethod.evidence),
        annotation_type: value.parse(annotationMethod.annotation_type),
        reference: value.parse(annotationMethod.reference, reference => ({
            content: '',
            option: {
                projectId,
                reference: getResourceService.getReference(projectBook, reference)
            }
        }))
    }
}

export const parseAnalyzedResultFile = (projectBook, analyzedResultFile) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(analyzedResultFile.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'analyzed_result_file',
                resourceId: id
            }
        })),
        data_analysis_id: value.parse(analyzedResultFile.data_analysis_id, dataAnalysisId => ({
            content: dataAnalysisId,
            option: {
                projectId,
                resourceName: 'data_analysis',
                resourceId: dataAnalysisId
            }
        })),
        data_analysis_name: value.parse(analyzedResultFile.data_analysis_name, dataAnalysisName => ({
            content: dataAnalysisName,
            option: {
                projectId,
                resourceName: 'data_analysis',
                resourceId: getService.getSheetId(projectBook, 'data_analysis', 'name', dataAnalysisName)
            }
        })),
        data_type: value.parse(analyzedResultFile.data_type),
        name: value.parse(analyzedResultFile.name),
        description: value.parse(analyzedResultFile.description),
        file_name: value.parse(analyzedResultFile.file_name),
        file_format: value.parse(analyzedResultFile.file_format),
        download_url: value.parse(analyzedResultFile.download_url, downloadUrl => ({
            content: downloadUrl,
            option: {
                href: downloadUrl
            }
        })),
        md5: value.parse(analyzedResultFile.md5)
    }
}

export const parsePerson = (projectBook, person) => {
    const projectId = getService.getId(projectBook)
    return {
        label: value.parse(person.label),
        label_ja: value.parse(person.label_ja),
        name_first: value.parse(person.name_first),
        name_family_ja: value.parse(person.name_family_ja),
        name_first_ja: value.parse(person.name_first_ja),
        name_middle: value.parse(person.name_middle),
        organisation: value.parse(person.organisation, organisation => ({
            content: organisation,
            option: {
                projectId,
                resourceName: 'organisation',
                resourceId: getService.getResourceId(projectBook, 'organisation', 'id', organisation)
            }
        })),
        email: value.parse(person.email),
        orcid: value.parse(person.orcid)
    }
}

export const parseOrganisation = (projectBook, organisation) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(organisation.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'organisation',
                resourceId: id
            }
        })),
        name: value.parse(organisation.name),
        name_ja: value.parse(organisation.name_ja),
        abbreviation: value.parse(organisation.abbreviation),
        homepage: value.parse(organisation.homepage),
        address: value.parse(organisation.address),
        address_ja: value.parse(organisation.address_ja),
        description: value.parse(organisation.description)
    }
}

export const parseFile = (projectBook, file) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(file.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'file',
                resourceId: id
            }
        })),
        name: value.parse(file.name),
        description: value.parse(file.description),
        local_folder_path: value.parse(file.local_folder_path),
        file_name: value.parse(file.file_name),
        file_format: value.parse(file.file_format),
        download_url: value.parse(file.download_url),
        md5: value.parse(file.md5)
    }
}

export const parseFileFormat = (projectBook, fileFormat) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(fileFormat.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'file_format',
                resourceId: id
            }
        })),
        name: value.parse(fileFormat.name),
        comment: value.parse(fileFormat.comment)
    }
}

export const parseUnit = (projectBook, unit) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(unit.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'unit',
                resourceId: id
            }
        })),
        name: value.parse(unit.name),
        super_class: value.parse(unit.super_class)
    }
}

export const parseUnitValue = (projectBook, unitValue) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(unitValue.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'unit_value',
                resourceId: id
            }
        })),
        name: value.parse(unitValue.name),
        type: value.parse(unitValue.type),
        value: value.parse(unitValue.value),
        unit: value.parse(unitValue.unit)
    }
}

export const parseReference = (projectBook, reference) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(reference.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'reference',
                resourceId: id
            }
        })),
        title: value.parse(reference.title),
        title_ja: value.parse(reference.title_ja),
        authors: value.parse(reference.authors),
        authors_ja: value.parse(reference.authors_ja),
        journal: value.parse(reference.journal),
        journal_ja: value.parse(reference.journal_ja),
        year: value.parse(reference.year),
        volume: value.parse(reference.volume),
        issue: value.parse(reference.issue),
        pages: value.parse(reference.pages),
        doi: value.parse(reference.doi),
        pmid: value.parse(reference.pmid)
    }
}

export const parseSoftware = (projectBook, software) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(software.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'software',
                resourceId: id
            }
        })),
        name: value.parse(software.name),
        version: value.parse(software.version),
        supplier: value.parse(software.supplier),
        product_id: value.parse(software.product_id),
        available_url: value.parse(software.available_url)
    }
}

export const parseTerm = (projectBook, term) => {
    return {
        name: value.parse(term.name),
        type: value.parse(term.type),
        ontology_uri: value.parse(term.ontology_uri, ontologyUri => ({
            content: ontologyUri,
            option: {
                href: ontologyUri
            }
        })),
        comment: value.parse(term.comment),
        description: value.parse(term.description)
    }
}

export const parseType = (projectBook, type) => {
    return {
        unit_value: value.parse(type.unit_value),
        terms: value.parse(type.terms),
        instrument_type: value.parse(type.instrument_type),
        column_type: value.parse(type.column_type),
        ms_instrument_type: value.parse(type.ms_instrument_type),
        ion_source: value.parse(type.ion_source),
        ionisation_polarity: value.parse(type.ionisation_polarity),
        scan_type: value.parse(type.scan_type),
        msn_aqcuisition_type: value.parse(type.msn_aqcuisition_type),
        fragmentation_method: value.parse(type.fragmentation_method),
        data_type: value.parse(type.data_type),
        data_processing_type: value.parse(type.data_processing_type),
        data_processing_method_type: value.parse(type.data_processing_method_type),
        annotation_type: value.parse(type.annotation_type),
        data_category: value.parse(type.data_category),
        gc_column_type: value.parse(type.gc_column_type),
        lc_column_type: value.parse(type.lc_column_type)
    }
}

export const parseSupplementaryFile = (projectBook, supplementaryFile) => {
    const projectId = getService.getId(projectBook)
    return {
        id: value.parse(supplementaryFile.id, id => ({
            content: id,
            option: {
                projectId,
                resourceName: 'supplementary_file',
                resourceId: id
            }
        })),
        name: value.parse(supplementaryFile.name),
        description: value.parse(supplementaryFile.description),
        file_name: value.parse(supplementaryFile.file_name),
        file_format: value.parse(supplementaryFile.file_format),
        download_url: value.parse(supplementaryFile.download_url, downloadUrl => ({
            content: downloadUrl,
            option: {
                href: downloadUrl
            }
        })),
        md5: value.parse(supplementaryFile.md5)
    }
}
