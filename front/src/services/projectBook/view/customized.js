import * as getService from '../get'
import * as getResourceService from '../get/resource'
import * as parseValueService from './parse/value'
import {getOrganisationList} from "./index";
import {getOrganisation} from "../get/resource";
import * as constant from "../constant";

export const getProject = project => {
    const id = project.ids.id
    const title = getTitle(project)
    const contact = getContact(project)
    const experiments = getExperiments(project)

    return {
        id: id,
        title: title,
        person_contact: {
            id: contact && contact.id ? contact.id : null,
            label: contact
                && contact.data
                && contact.data.name_first
                && contact.data.name_family
                ? contact.data.name_first[0] + " " + contact.data.name_family[0] : null
        },
        published_date: project.publishedDate,
        experiments: experiments,
        hidden: project.hidden,
        editing: project.editing,
    }
}

export const getTitle = project => {
    if(project.editing && project.draftListdata && project.draftListdata.titles) {
        return project.draftListdata.titles.title[0]
    }

    return project.listdata ? project.listdata.titles.title[0] : null
}

export const getContact = project => {
    if(project.editing && project.draftListdata && project.draftListdata.persons) {
        return project.draftListdata.persons.contact
    }

    return project.listdata ? project.listdata.persons.contact : null
}

export const getExperiments = project => {
    if(project.editing && project.draftAggregate && project.draftAggregate) {
        return project.draftAggregate.experiments
    }

    return project.aggregate ? project.aggregate.experiments : null
}

export const getPersonList = projectBook => {
    const projectId = getService.getId(projectBook)
    const rows = getResourceService.getPersonList(projectBook)

    return rows.map(row => {
        const id = row.id ? row.id[0] : null

        const organisationList = getResourceService.getOrganisationList(projectBook)

        const organisations = row.organisation ? (
            row.organisation
                .map(organisationId => {

                    const organisation = organisationList.filter(org => org.id[0] === organisationId || (org.label && org.label[0] === organisationId) || (org.name && org.name[0] === organisationId))
                    return organisation ? organisation[0] : {}
                })
                .filter(Boolean)
        ) : []

        return {
            name: parseValueService.parse(row.label, label => ({
                content: label,
                option: {
                    projectId,
                    resourceName: 'person',
                    resourceId: id
                }
            })),
            affiliation: parseValueService.parse(organisations, organisation => ({
                content: organisation.name[0],
                option: {
                    href: organisation.homepage ? organisation.homepage[0] : null,
                }
            }))
        }
    })
}

export const getReferenceList = (projectBook) => {
    const rows = getResourceService.getReferenceList(projectBook)
    const projectId = getService.getId(projectBook)

    return rows.map(row => {
        return {
            id: parseValueService.parse(row.id, id => ({
                content: id,
                option: {
                    projectId,
                    resourceName: 'reference',
                    resourceId: id
                }
            })),
            reference: parseValueService.parse([row], reference => ({
                content: '',
                option: {
                    projectId,
                    reference,
                }
            }))
        }
    })
}

export const getExperimentList = (projectBook) => {
    const rows = getResourceService.getExperimentList(projectBook)
    const projectId = getService.getId(projectBook)

    return rows.map(row => {
        const experimentalDesign = row.experimental_design
            .map(experimentalDesignId => getResourceService.getExperimentalDesign(projectBook, experimentalDesignId))
            .filter(Boolean)

        return {
            id: parseValueService.parse(row.id, id => ({
                content: id,
                option: {
                    projectId,
                    resourceName: 'experiment',
                    resourceId: id,
                }
            })),
            name: parseValueService.parse(row.name),
            measurement_type: parseValueService.parse(row.measurement_type),
            technology_type: parseValueService.parse(row.technology_type),
            technology_platform: parseValueService.parse(row.technology_platform),
            experimental_design: parseValueService.parse(experimentalDesign, experimentalDesign => ({
                content: experimentalDesign.id,
                option: {
                    projectId,
                    resourceName: 'experimental_design',
                    resourceId: experimentalDesign.id
                }
            })),
        }
    })
}

export const getExperimentalDesignList = (projectBook) => {
    const rows = getResourceService.getExperimentalDesignList(projectBook)
    const projectId = getService.getId(projectBook)

    return rows.map(experimentalDesign => ({
        name: parseValueService.parse([experimentalDesign], experimentalDesign => ({
            content: experimentalDesign && experimentalDesign.name ? experimentalDesign.name[0] : null,
            option: {
                projectId,
                resourceName: 'experimental_design',
                resourceId: experimentalDesign.id[0],
            }
        }))
    }))
}

export const getMeasurementList = (projectBook) => {
    const rows = getResourceService.getMeasurementList(projectBook)
    const projectId = getService.getId(projectBook)

    return rows.map(measurement => {
        return {
            id: parseValueService.parse(measurement.id, measurementId => {
                return {
                    content: measurementId,
                    option: {
                        projectId,
                        resourceName: 'measurement',
                        resourceId: measurementId
                    }
                }
            }),
            experiment: parseValueService.parse(measurement.experiment_id, experimentId => {
                const experiment = getResourceService.getExperiment(projectBook, experimentId)
                return {
                    content: experiment && experiment.id && experiment.name ? `${experiment.id[0]}: ${experiment.name[0]}` : null,
                    option: {
                        projectId,
                        resourceName: 'experiment',
                        resourceId: experimentId
                    }
                }
            }),
            sample: parseValueService.parse(measurement.sample_id, sampleId => {
                const sample = getResourceService.getSample(projectBook, sampleId)
                return {
                    content: sample && sample.id && sample.name ? `${sample.id[0]}: ${sample.name[0]}` : null,
                    option: {
                        projectId,
                        resourceName: 'sample',
                        resourceId: sampleId
                    }
                }
            }),
            extract_id: parseValueService.parse(measurement.extract_id),
            extraction_method: parseValueService.parse(measurement.extraction_method_id, extractionMethodId => {
                const extractionMethod = getResourceService.getExtractionMethod(projectBook, extractionMethodId)
                return {
                    content: extractionMethod && extractionMethod.id && extractionMethod.name ? `${extractionMethod.id[0]}: ${extractionMethod.name[0]}` : null,
                    option: {
                        projectId,
                        resourceName: 'extraction_method',
                        resourceId: extractionMethodId
                    }
                }
            }),
            measurement_id: parseValueService.parse(measurement.measurement_id),
            measurement_method: parseValueService.parse(measurement.measurement_method_id, measurementMethodId => {
                const measurementMethod = getResourceService.getMeasurementMethod(projectBook, measurementMethodId)

                return {
                    content: measurementMethod && measurementMethod.name && measurementMethod.id ? `${measurementMethod.id[0]} ${measurementMethod.name[0]}` : null,
                    option: {
                        projectId,
                        resourceName: 'measurement_method',
                        resourceId: measurementMethodId
                    }
                }
            }),
        }
    })
}
