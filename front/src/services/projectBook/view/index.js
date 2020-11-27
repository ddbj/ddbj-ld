import * as getResourceService from '../get/resource'
import * as parseService from './parse'

import {getData, getDataList} from './core'

export const getProject = (projectBook) => {
    const row = getResourceService.getProject(projectBook)
    return parseService.parseProject(projectBook, row)
}

export const getExperiment = (projectBook, experimentId) =>
    getData(projectBook, getResourceService.getExperiment(projectBook, experimentId), parseService.parseExperiment)

export const getExperimentList = projectBook =>
    getDataList(projectBook, getResourceService.getExperimentList(projectBook), parseService.parseExperiment)

export const getExperimentalDesign = (projectBook, experimentalDesignId) =>
    getData(projectBook, getResourceService.getExperimentalDesign(projectBook, experimentalDesignId), parseService.parseExperimentalDesign)

export const getExperimentalDesignList = projectBook =>
    getDataList(projectBook, getResourceService.getExperimentalDesignList(projectBook), parseService.parseExperimentalDesign)

export const getSamplePrep = (projectBook, samplePrepId) =>
    getData(projectBook, getResourceService.getSamplePrep(projectBook, samplePrepId), parseService.parseSamplePrep)

export const getSamplePrepList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepList(projectBook), parseService.parseSamplePrep)

export const getSamplePrepAnimal = (projectBook, samplePrepAnimalId) =>
    getData(projectBook, getResourceService.getSamplePrepAnimal(projectBook, samplePrepAnimalId), parseService.parseSamplePrepAnimal)

export const getSamplePrepAnimalList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepAnimalList(projectBook), parseService.parseSamplePrepAnimal)

export const getSamplePrepPlant = (projectBook, samplePrepPlantId) =>
    getData(projectBook, getResourceService.getSamplePrepPlant(projectBook, samplePrepPlantId), parseService.parseSamplePrepPlant)

export const getSamplePrepPlantList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepPlantList(projectBook), parseService.parseSamplePrepPlant)

export const getSamplePrepChemical = (projectBook, samplePrepChemicalId) =>
    getData(projectBook, getResourceService.getSamplePrepChemical(projectBook, samplePrepChemicalId), parseService.parseSamplePrepChemical)

export const getSamplePrepChemicalList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepChemicalList(projectBook), parseService.parseSamplePrepChemical)

export const getSamplePrepOther = (projectBook, samplePrepOtherId) =>
    getData(projectBook, getResourceService.getSamplePrepOther(projectBook, samplePrepOtherId), parseService.parseSamplePrepOther)

export const getSamplePrepOtherList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepOtherList(projectBook), parseService.parseSamplePrepOther)

export const getLightCondition = (projectBook, lightConditionId) =>
    getData(projectBook, getResourceService.getLightCondition(projectBook, lightConditionId), parseService.parseLightCondition)

export const getLightConditionList = projectBook =>
    getDataList(projectBook, getResourceService.getLightConditionList(projectBook), parseService.parseLightCondition)

export const getSamplePrepClinical = (projectBook, samplePrepClinicalId) =>
    getData(projectBook, getResourceService.getSamplePrepClinical(projectBook, samplePrepClinicalId), parseService.parseSamplePrepClinical)

export const getSamplePrepClinicalList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepClinicalList(projectBook), parseService.parseSamplePrepClinical)

export const getSamplePrepBacteria = (projectBook, samplePrepBacteriaId) =>
    getData(projectBook, getResourceService.getSamplePrepBacteria(projectBook, samplePrepBacteriaId), parseService.parseSamplePrepBacteria)

export const getSamplePrepBacteriaList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepBacteriaList(projectBook), parseService.parseSamplePrepBacteria)

export const getSamplePrepEnvironment = (projectBook, samplePrepEnvironmentId) =>
    getData(projectBook, getResourceService.getSamplePrepEnvironment(projectBook, samplePrepEnvironmentId), parseService.parseSamplePrepEnvironment)

export const getSamplePrepEnvironmentList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepEnvironmentList(projectBook), parseService.parseSamplePrepEnvironment)

export const getSamplePrepFood = (projectBook, samplePrepFoodId) =>
    getData(projectBook, getResourceService.getSamplePrepFood(projectBook, samplePrepFoodId), parseService.parseSamplePrepFood)

export const getSamplePrepFoodList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepFoodList(projectBook), parseService.parseSamplePrepFood)

export const getSamplePrepControl = (projectBook, samplePrepControlId) =>
    getData(projectBook, getResourceService.getSamplePrepControl(projectBook, samplePrepControlId), parseService.parseSamplePrepControl)

export const getSamplePrepControlList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePrepControlList(projectBook), parseService.parseSamplePrepControl)

export const getSample = (projectBook, sampleId) =>
    getData(projectBook, getResourceService.getSample(projectBook, sampleId), parseService.parseSample)

export const getSampleList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleList(projectBook), parseService.parseSample)

export const getSampleAnimal = (projectBook, sampleAnimalId) =>
    getData(projectBook, getResourceService.getSampleAnimal(projectBook, sampleAnimalId), parseService.parseSampleAnimal)

export const getSampleAnimalList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleAnimalList(projectBook), parseService.parseSampleAnimal)

export const getSamplePlant = (projectBook, samplePlantId) =>
    getData(projectBook, getResourceService.getSamplePlant(projectBook, samplePlantId), parseService.parseSamplePlant)

export const getSamplePlantList = projectBook =>
    getDataList(projectBook, getResourceService.getSamplePlantList(projectBook), parseService.parseSamplePlant)

export const getSampleChemical = (projectBook, sampleChemicalId) =>
    getData(projectBook, getResourceService.getSampleChemical(projectBook, sampleChemicalId), parseService.parseSampleChemical)

export const getSampleChemicalList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleChemicalList(projectBook), parseService.parseSampleChemical)

export const getSampleOther = (projectBook, sampleOtherId) =>
    getData(projectBook, getResourceService.getSampleOther(projectBook, sampleOtherId), parseService.parseSampleOther)

export const getSampleOtherList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleOtherList(projectBook), parseService.parseSampleOther)

export const getSampleClinical = (projectBook, sampleClinicalId) =>
    getData(projectBook, getResourceService.getSampleClinical(projectBook, sampleClinicalId), parseService.parseSampleClinical)

export const getSampleClinicalList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleClinicalList(projectBook), parseService.parseSampleClinical)

export const getSampleBacteria = (projectBook, sampleBacteriaId) =>
    getData(projectBook, getResourceService.getSampleBacteria(projectBook, sampleBacteriaId), parseService.parseSampleBacteria)

export const getSampleBacteriaList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleBacteriaList(projectBook), parseService.parseSampleBacteria)

export const getSampleEnvironment = (projectBook, sampleEnvironmentId) =>
    getData(projectBook, getResourceService.getSampleEnvironment(projectBook, sampleEnvironmentId), parseService.parseSampleEnvironment)

export const getSampleEnvironmentList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleEnvironmentList(projectBook), parseService.parseSampleEnvironment)

export const getSampleFood = (projectBook, sampleFoodId) =>
    getData(projectBook, getResourceService.getSampleFood(projectBook, sampleFoodId), parseService.parseSampleFood)

export const getSampleFoodList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleFoodList(projectBook), parseService.parseSampleFood)

export const getSampleControl = (projectBook, sampleControlId) =>
    getData(projectBook, getResourceService.getSampleControl(projectBook, sampleControlId), parseService.parseSampleControl)

export const getSampleControlList = projectBook =>
    getDataList(projectBook, getResourceService.getSampleControlList(projectBook), parseService.parseSampleControl)

export const getTreatment = (projectBook, treatmentId) =>
    getData(projectBook, getResourceService.getTreatment(projectBook, treatmentId), parseService.parseTreatment)

export const getTreatmentList = projectBook =>
    getDataList(projectBook, getResourceService.getTreatmentList(projectBook), parseService.parseTreatment)

export const getExtractionMethod = (projectBook, extractionMethodId) =>
    getData(projectBook, getResourceService.getExtractionMethod(projectBook, extractionMethodId), parseService.parseExtractionMethod)

export const getExtractionMethodList = projectBook =>
    getDataList(projectBook, getResourceService.getExtractionMethodList(projectBook), parseService.parseExtractionMethod)

export const getMeasurementMethod = (projectBook, measurementMethodId) =>
    getData(projectBook, getResourceService.getMeasurementMethod(projectBook, measurementMethodId), parseService.parseMeasurementMethod)

export const getMeasurementMethodList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementMethodList(projectBook), parseService.parseMeasurementMethod)

export const getMeasurementMethodLcms = (projectBook, measurementMethodLcmsId) =>
    getData(projectBook, getResourceService.getMeasurementMethodLcms(projectBook, measurementMethodLcmsId), parseService.parseMeasurementMethodLcms)

export const getMeasurementMethodLcmsList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementMethodLcmsList(projectBook), parseService.parseMeasurementMethodLcms)

export const getMeasurementMethodGcms = (projectBook, measurementMethodGcmsId) =>
    getData(projectBook, getResourceService.getMeasurementMethodGcms(projectBook, measurementMethodGcmsId), parseService.parseMeasurementMethodGcms)

export const getMeasurementMethodGcmsList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementMethodGcmsList(projectBook), parseService.parseMeasurementMethodGcms)

export const getMeasurementMethodCems = (projectBook, measurementMethodCemsId) =>
    getData(projectBook, getResourceService.getMeasurementMethodCems(projectBook, measurementMethodCemsId), parseService.parseMeasurementMethodCems)

export const getMeasurementMethodCemsList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementMethodCemsList(projectBook), parseService.parseMeasurementMethodCems)

export const getMeasurementMethodMs = (projectBook, measurementMethodMsId) =>
    getData(projectBook, getResourceService.getMeasurementMethodMs(projectBook, measurementMethodMsId), parseService.parseMeasurementMethodMs)

export const getMeasurementMethodMsList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementMethodMsList(projectBook), parseService.parseMeasurementMethodMs)

export const getMeasurementMethodNmr = (projectBook, measurementMethodNmrId) =>
    getData(projectBook, getResourceService.getMeasurementMethodNmr(projectBook, measurementMethodNmrId), parseService.parseMeasurementMethodNmr)

export const getMeasurementMethodNmrList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementMethodNmrList(projectBook), parseService.parseMeasurementMethodNmr)

export const getMeasurementMethodOther = (projectBook, measurementMethodOtherId) =>
    getData(projectBook, getResourceService.getMeasurementMethodOther(projectBook, measurementMethodOtherId), parseService.parseMeasurementMethodOther)

export const getMeasurementMethodOtherList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementMethodOtherList(projectBook), parseService.parseMeasurementMethodOther)

export const getConditionLc = (projectBook, conditionLcId) =>
    getData(projectBook, getResourceService.getConditionLc(projectBook, conditionLcId), parseService.parseConditionLc)

export const getConditionLcList = projectBook =>
    getDataList(projectBook, getResourceService.getConditionLcList(projectBook), parseService.parseConditionLc)

export const getConditionGc = (projectBook, conditionGcId) =>
    getData(projectBook, getResourceService.getConditionGc(projectBook, conditionGcId), parseService.parseConditionGc)

export const getConditionGcList = projectBook =>
    getDataList(projectBook, getResourceService.getConditionGcList(projectBook), parseService.parseConditionGc)

export const getConditionCe = (projectBook, conditionCeId) =>
    getData(projectBook, getResourceService.getConditionCe(projectBook, conditionCeId), parseService.parseConditionCe)

export const getConditionCeList = projectBook =>
    getDataList(projectBook, getResourceService.getConditionCeList(projectBook), parseService.parseConditionCe)

export const getConditionMs = (projectBook, conditionMsId) =>
    getData(projectBook, getResourceService.getConditionMs(projectBook, conditionMsId), parseService.parseConditionMs)

export const getConditionMsList = projectBook =>
    getDataList(projectBook, getResourceService.getConditionMsList(projectBook), parseService.parseConditionMs)

export const getConditionNmr = (projectBook, conditionNmrId) =>
    getData(projectBook, getResourceService.getConditionNmr(projectBook, conditionNmrId), parseService.parseConditionNmr)

export const getConditionNmrList = projectBook =>
    getDataList(projectBook, getResourceService.getConditionNmrList(projectBook), parseService.parseConditionNmr)

export const getConditionOther = (projectBook, conditionOtherId) =>
    getData(projectBook, getResourceService.getConditionOther(projectBook, conditionOtherId), parseService.parseConditionOther)

export const getConditionOtherList = projectBook =>
    getDataList(projectBook, getResourceService.getConditionOtherList(projectBook), parseService.parseConditionOther)

export const getInstrument = (projectBook, instrumentId) =>
    getData(projectBook, getResourceService.getInstrument(projectBook, instrumentId), parseService.parseInstrument)

export const getInstrumentList = projectBook =>
    getDataList(projectBook, getResourceService.getInstrumentList(projectBook), parseService.parseInstrument)

export const getColumn = (projectBook, columnId) =>
    getData(projectBook, getResourceService.getColumn(projectBook, columnId), parseService.parseColumn)

export const getColumnList = projectBook =>
    getDataList(projectBook, getResourceService.getColumnList(projectBook), parseService.parseColumn)

export const getMeasurement = (projectBook, measurementId) =>
    getData(projectBook, getResourceService.getMeasurement(projectBook, measurementId), parseService.parseMeasurement)

export const getMeasurementList = projectBook =>
    getDataList(projectBook, getResourceService.getMeasurementList(projectBook), parseService.parseMeasurement)

export const getRawDataFile = (projectBook, rawDataFileId) =>
    getData(projectBook, getResourceService.getRawDataFile(projectBook, rawDataFileId), parseService.parseRawDataFile)

export const getRawDataFileList = projectBook =>
    getDataList(projectBook, getResourceService.getRawDataFileList(projectBook), parseService.parseRawDataFile)

export const getDataAnalysis = (projectBook, dataAnalysisId) =>
    getData(projectBook, getResourceService.getDataAnalysis(projectBook, dataAnalysisId), parseService.parseDataAnalysis)

export const getDataAnalysisList = projectBook =>
    getDataList(projectBook, getResourceService.getDataAnalysisList(projectBook), parseService.parseDataAnalysis)

export const getAnalyzedRawFile = (projectBook, analyzedRawFileId) =>
    getData(projectBook, getResourceService.getAnalyzedRawFile(projectBook, analyzedRawFileId), parseService.parseAnalyzedRawFile)

export const getAnalyzedRawFileList = projectBook =>
    getDataList(projectBook, getResourceService.getAnalyzedRawFileList(projectBook), parseService.parseAnalyzedRawFile)

export const getDataPreprocessing = (projectBook, dataPreprocessingId) =>
    getData(projectBook, getResourceService.getDataPreprocessing(projectBook, dataPreprocessingId), parseService.parseDataPreprocessing)

export const getDataPreprocessingList = projectBook =>
    getDataList(projectBook, getResourceService.getDataPreprocessingList(projectBook), parseService.parseDataPreprocessing)

export const getDataProcessingMethod = (projectBook, dataProcessingMethodId) =>
    getData(projectBook, getResourceService.getDataProcessingMethod(projectBook, dataProcessingMethodId), parseService.parseDataProcessingMethod)

export const getDataProcessingMethodList = projectBook =>
    getDataList(projectBook, getResourceService.getDataProcessingMethodList(projectBook), parseService.parseDataProcessingMethod)

export const getDataProcessingMethodType = (projectBook, dataProcessingMethodTypeId) =>
    getData(projectBook, getResourceService.getDataProcessingMethodType(projectBook, dataProcessingMethodTypeId), parseService.parseDataProcessingMethodType)

export const getDataProcessingMethodTypeList = projectBook =>
    getDataList(projectBook, getResourceService.getDataProcessingMethodTypeList(projectBook), parseService.parseDataProcessingMethodType)

export const getAnnotationMethod = (projectBook, annotationMethodId) =>
    getData(projectBook, getResourceService.getAnnotationMethod(projectBook, annotationMethodId), parseService.parseAnnotationMethod)

export const getAnnotationMethodList = projectBook =>
    getDataList(projectBook, getResourceService.getAnnotationMethodList(projectBook), parseService.parseAnnotationMethod)

export const getAnalyzedResultFile = (projectBook, analyzedResultFileId) => {


    return getData(projectBook, getResourceService.getAnalyzedResultFile(projectBook, analyzedResultFileId), parseService.parseAnalyzedResultFile)
}

export const getAnalyzedResultFileList = projectBook =>
    getDataList(projectBook, getResourceService.getAnalyzedResultFileList(projectBook), parseService.parseAnalyzedResultFile)

export const getPerson = (projectBook, personId) =>
    getData(projectBook, getResourceService.getPerson(projectBook, personId), parseService.parsePerson)

export const getPersonList = projectBook =>
    getDataList(projectBook, getResourceService.getPersonList(projectBook), parseService.parsePerson)

export const getOrganisation = (projectBook, organisationId) =>
    getData(projectBook, getResourceService.getOrganisation(projectBook, organisationId), parseService.parseOrganisation)

export const getOrganisationList = projectBook =>
    getDataList(projectBook, getResourceService.getOrganisationList(projectBook), parseService.parseOrganisation)

export const getFile = (projectBook, fileId) =>
    getData(projectBook, getResourceService.getFile(projectBook, fileId), parseService.parseFile)

export const getFileList = projectBook =>
    getDataList(projectBook, getResourceService.getFileList(projectBook), parseService.parseFile)

export const getFileFormat = (projectBook, fileFormatId) =>
    getData(projectBook, getResourceService.getFileFormat(projectBook, fileFormatId), parseService.parseFileFormat)

export const getFileFormatList = projectBook =>
    getDataList(projectBook, getResourceService.getFileFormatList(projectBook), parseService.parseFileFormat)

export const getUnit = (projectBook, unitId) =>
    getData(projectBook, getResourceService.getUnit(projectBook, unitId), parseService.parseUnit)

export const getUnitList = projectBook =>
    getDataList(projectBook, getResourceService.getUnitList(projectBook), parseService.parseUnit)

export const getUnitValue = (projectBook, unitValueId) =>
    getData(projectBook, getResourceService.getUnitValue(projectBook, unitValueId), parseService.parseUnitValue)

export const getUnitValueList = projectBook =>
    getDataList(projectBook, getResourceService.getUnitValueList(projectBook), parseService.parseUnitValue)

export const getReference = (projectBook, referenceId) =>
    getData(projectBook, getResourceService.getReference(projectBook, referenceId), parseService.parseReference)

export const getReferenceList = projectBook =>
    getDataList(projectBook, getResourceService.getReferenceList(projectBook), parseService.parseReference)

export const getSoftware = (projectBook, softwareId) =>
    getData(projectBook, getResourceService.getSoftware(projectBook, softwareId), parseService.parseSoftware)

export const getSoftwareList = projectBook =>
    getDataList(projectBook, getResourceService.getSoftwareList(projectBook), parseService.parseSoftware)

export const getTerm = (projectBook, termId) =>
    getData(projectBook, getResourceService.getTerm(projectBook, termId), parseService.parseTerm)

export const getTermList = projectBook =>
    getDataList(projectBook, getResourceService.getTermList(projectBook), parseService.parseTerm)

export const getType = (projectBook, typeId) =>
    getData(projectBook, getResourceService.getType(projectBook, typeId), parseService.parseType)

export const getTypeList = projectBook =>
    getDataList(projectBook, getResourceService.getTypeList(projectBook), parseService.parseType)

export const getSupplementaryFile = (projectBook, supplementaryFileId) =>
    getData(projectBook, getResourceService.getSupplementaryFile(projectBook, supplementaryFileId), parseService.parseSupplementaryFile)

export const getSupplementaryFileList = projectBook =>
    getDataList(projectBook, getResourceService.getSupplementaryFileList(projectBook), parseService.parseSupplementaryFile)