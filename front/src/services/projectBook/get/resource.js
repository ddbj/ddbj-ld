import {getSheetRow, getSheetRows, getSheetRowWithKeyAndValue} from '.'

import * as constant from '../constant'

export const getProject = (projectBook) => {
    const rows = getSheetRows(projectBook, constant.SHEET_ID_PROJECT)
    return rows[0]
}

export const getExperiment = (projectBook, experimentId) =>
    getSheetRow(projectBook, constant.SHEET_ID_EXPERIMENT, experimentId)

export const getExperimentList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_EXPERIMENT)

export const getExperimentalDesign = (projectBook, experimentalDesignId) =>
    getSheetRow(projectBook, constant.SHEET_ID_EXPERIMENTAL_DESIGN, experimentalDesignId)

export const getExperimentalDesignList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_EXPERIMENTAL_DESIGN)

export const getSamplePrep = (projectBook, samplePrepId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP, samplePrepId)

export const getSamplePrepList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP)

export const getSamplePrepAnimal = (projectBook, samplePrepAnimalId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_ANIMAL, samplePrepAnimalId)

export const getSamplePrepAnimalList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_ANIMAL)

export const getSamplePrepPlant = (projectBook, samplePrepPlantId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_PLANT, samplePrepPlantId)

export const getSamplePrepPlantList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_PLANT)

export const getSamplePrepChemical = (projectBook, samplePrepChemicalId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_CHEMICAL, samplePrepChemicalId)

export const getSamplePrepChemicalList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_CHEMICAL)

export const getSamplePrepOther = (projectBook, samplePrepOtherId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_OTHER, samplePrepOtherId)

export const getSamplePrepOtherList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_OTHER)

export const getLightCondition = (projectBook, lightConditionId) =>
    getSheetRow(projectBook, constant.SHEET_ID_LIGHT_CONDITION, lightConditionId)

export const getLightConditionList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_LIGHT_CONDITION)

export const getSamplePrepClinical = (projectBook, samplePrepClinicalId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_CLINICAL, samplePrepClinicalId)

export const getSamplePrepClinicalList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_CLINICAL)

export const getSamplePrepBacteria = (projectBook, samplePrepBacteriaId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_BACTERIA, samplePrepBacteriaId)

export const getSamplePrepBacteriaList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_BACTERIA)

export const getSamplePrepEnvironment = (projectBook, samplePrepEnvironmentId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_ENVIRONMENT, samplePrepEnvironmentId)

export const getSamplePrepEnvironmentList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_ENVIRONMENT)

export const getSamplePrepFood = (projectBook, samplePrepFoodId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_FOOD, samplePrepFoodId)

export const getSamplePrepFoodList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_FOOD)

export const getSamplePrepControl = (projectBook, samplePrepControlId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PREP_CONTROL, samplePrepControlId)

export const getSamplePrepControlList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PREP_CONTROL)

export const getSample = (projectBook, sampleId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE, sampleId)

export const getSampleList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE)

export const getSampleAnimal = (projectBook, sampleAnimalId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_ANIMAL, sampleAnimalId)

export const getSampleAnimalList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_ANIMAL)

export const getSamplePlant = (projectBook, samplePlantId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_PLANT, samplePlantId)

export const getSamplePlantList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_PLANT)

export const getSampleChemical = (projectBook, sampleChemicalId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_CHEMICAL, sampleChemicalId)

export const getSampleChemicalList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_CHEMICAL)

export const getSampleOther = (projectBook, sampleOtherId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_OTHER, sampleOtherId)

export const getSampleOtherList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_OTHER)

export const getSampleClinical = (projectBook, sampleClinicalId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_CLINICAL, sampleClinicalId)

export const getSampleClinicalList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_CLINICAL)

export const getSampleBacteria = (projectBook, sampleBacteriaId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_BACTERIA, sampleBacteriaId)

export const getSampleBacteriaList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_BACTERIA)

export const getSampleEnvironment = (projectBook, sampleEnvironmentId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_ENVIRONMENT, sampleEnvironmentId)

export const getSampleEnvironmentList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_ENVIRONMENT)

export const getSampleFood = (projectBook, sampleFoodId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_FOOD, sampleFoodId)

export const getSampleFoodList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_FOOD)

export const getSampleControl = (projectBook, sampleControlId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SAMPLE_CONTROL, sampleControlId)

export const getSampleControlList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SAMPLE_CONTROL)

export const getTreatment = (projectBook, treatmentId) =>
    getSheetRow(projectBook, constant.SHEET_ID_TREATMENT, treatmentId)

export const getTreatmentList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_TREATMENT)

export const getExtractionMethod = (projectBook, extractionMethodId) =>
    getSheetRow(projectBook, constant.SHEET_ID_EXTRACTION_METHOD, extractionMethodId)

export const getExtractionMethodList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_EXTRACTION_METHOD)

export const getMeasurementMethod = (projectBook, measurementMethodId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD, measurementMethodId)

export const getMeasurementMethodList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD)

export const getMeasurementMethodLcms = (projectBook, measurementMethodLcmsId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_LCMS, measurementMethodLcmsId)

export const getMeasurementMethodLcmsList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_LCMS)

export const getMeasurementMethodGcms = (projectBook, measurementMethodGcmsId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_GCMS, measurementMethodGcmsId)

export const getMeasurementMethodGcmsList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_GCMS)

export const getMeasurementMethodCems = (projectBook, measurementMethodCemsId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_CEMS, measurementMethodCemsId)

export const getMeasurementMethodCemsList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_CEMS)

export const getMeasurementMethodMs = (projectBook, measurementMethodMsId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_MS, measurementMethodMsId)

export const getMeasurementMethodMsList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_MS)

export const getMeasurementMethodNmr = (projectBook, measurementMethodNmrId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_NMR, measurementMethodNmrId)

export const getMeasurementMethodNmrList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_NMR)

export const getMeasurementMethodOther = (projectBook, measurementMethodOtherId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_OTHER, measurementMethodOtherId)

export const getMeasurementMethodOtherList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT_METHOD_OTHER)

export const getConditionLc = (projectBook, conditionLcId) =>
    getSheetRow(projectBook, constant.SHEET_ID_CONDITION_LC, conditionLcId)

export const getConditionLcList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_CONDITION_LC)

export const getConditionGc = (projectBook, conditionGcId) =>
    getSheetRow(projectBook, constant.SHEET_ID_CONDITION_GC, conditionGcId)

export const getConditionGcList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_CONDITION_GC)

export const getConditionCe = (projectBook, conditionCeId) =>
    getSheetRow(projectBook, constant.SHEET_ID_CONDITION_CE, conditionCeId)

export const getConditionCeList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_CONDITION_CE)

export const getConditionMs = (projectBook, conditionMsId) =>
    getSheetRow(projectBook, constant.SHEET_ID_CONDITION_MS, conditionMsId)

export const getConditionMsList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_CONDITION_MS)

export const getConditionNmr = (projectBook, conditionNmrId) =>
    getSheetRow(projectBook, constant.SHEET_ID_CONDITION_NMR, conditionNmrId)

export const getConditionNmrList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_CONDITION_NMR)

export const getConditionOther = (projectBook, conditionOtherId) =>
    getSheetRow(projectBook, constant.SHEET_ID_CONDITION_OTHER, conditionOtherId)

export const getConditionOtherList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_CONDITION_OTHER)

export const getInstrument = (projectBook, instrumentId) =>
    getSheetRow(projectBook, constant.SHEET_ID_INSTRUMENT, instrumentId)

export const getInstrumentList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_INSTRUMENT)

export const getColumn = (projectBook, columnId) =>
    getSheetRow(projectBook, constant.SHEET_ID_COLUMN, columnId)

export const getColumnList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_COLUMN)

export const getMeasurement = (projectBook, measurementId) =>
    getSheetRow(projectBook, constant.SHEET_ID_MEASUREMENT, measurementId)

export const getMeasurementList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_MEASUREMENT)

export const getRawDataFile = (projectBook, rawDataFileId) => {
    const rowDataFile = getSheetRow(projectBook, constant.SHEET_ID_RAW_DATA_FILE, rawDataFileId)

    if(null === rowDataFile && rowDataFile.file_name_download.length > 0) {
        return null
    }

    const downloadFiles = getSheetRows(projectBook, constant.SHEET_ID_RAW_DATA_DOWNLOAD_FILE, rawDataFileId)
    const downloadFile = downloadFiles
        ? getSheetRowWithKeyAndValue(projectBook, constant.SHEET_ID_RAW_DATA_DOWNLOAD_FILE, "file_name", rowDataFile.file_name_download[0])
        : {}

    // FIXME 将来的にシートの統廃合があるかもしれない
    const result = {
        ...rowDataFile,
        ...downloadFile
    }

    return result
}

export const getRawDataFileList = projectBook => {
    const rowDataFiles = getSheetRows(projectBook, constant.SHEET_ID_RAW_DATA_FILE)

    if(null === rowDataFiles) {
        return null
    }

    // FIXME 将来的にシートの統廃合があるかもしれない
    const result = rowDataFiles.map(rowDataFile => {
        if (! rowDataFile.file_name_download
            || 0 === rowDataFile.file_name_download.length) {

            return rowDataFile
        }

        const downloadFile = getSheetRowWithKeyAndValue(projectBook, constant.SHEET_ID_RAW_DATA_DOWNLOAD_FILE, "file_name", rowDataFile.file_name_download[0])

        return {
            ...downloadFile,
            ...rowDataFile
        }
    })

    return result
}

export const getDataAnalysis = (projectBook, dataAnalysisId) =>
    getSheetRow(projectBook, constant.SHEET_ID_DATA_ANALYSIS, dataAnalysisId)

export const getDataAnalysisList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_DATA_ANALYSIS)

export const getAnalyzedRawFile = (projectBook, analyzedRawFileId) =>
    getSheetRow(projectBook, constant.SHEET_ID_ANALYZED_RAW_FILE, analyzedRawFileId)

export const getAnalyzedRawFileList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_ANALYZED_RAW_FILE)

export const getDataPreprocessing = (projectBook, dataPreprocessingId) =>
    getSheetRow(projectBook, constant.SHEET_ID_DATA_PREPROCESSING, dataPreprocessingId)

export const getDataPreprocessingList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_DATA_PREPROCESSING)

export const getDataProcessingMethod = (projectBook, dataProcessingMethodId) =>
    getSheetRow(projectBook, constant.SHEET_ID_DATA_PROCESSING_METHOD, dataProcessingMethodId)

export const getDataProcessingMethodList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_DATA_PROCESSING_METHOD)

export const getDataProcessingMethodType = (projectBook, dataProcessingMethodTypeId) =>
    getSheetRow(projectBook, constant.SHEET_ID_DATA_PROCESSING_METHOD_TYPE, dataProcessingMethodTypeId)

export const getDataProcessingMethodTypeList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_DATA_PROCESSING_METHOD_TYPE)

export const getAnnotationMethod = (projectBook, annotationMethodId) =>
    getSheetRow(projectBook, constant.SHEET_ID_ANNOTATION_METHOD, annotationMethodId)

export const getAnnotationMethodList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_ANNOTATION_METHOD)

export const getAnalyzedResultFile = (projectBook, analyzedResultFileId) =>
    getSheetRow(projectBook, constant.SHEET_ID_ANALYZED_RESULT_FILE, analyzedResultFileId)

export const getAnalyzedResultFileList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_ANALYZED_RESULT_FILE)

export const getPerson = (projectBook, personId) =>
    getSheetRow(projectBook, constant.SHEET_ID_PERSON, personId)

export const getPersonList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_PERSON)

export const getOrganisation = (projectBook, organisationId) =>
    getSheetRow(projectBook, constant.SHEET_ID_ORGANISATION, organisationId)

export const getOrganisationList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_ORGANISATION)

export const getFile = (projectBook, fileId) =>
    getSheetRow(projectBook, constant.SHEET_ID_FILE, fileId)

export const getFileList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_FILE)

export const getFileFormat = (projectBook, fileFormatId) =>
    getSheetRow(projectBook, constant.SHEET_ID_FILE_FORMAT, fileFormatId)

export const getFileFormatList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_FILE_FORMAT)

export const getUnit = (projectBook, unitId) =>
    getSheetRow(projectBook, constant.SHEET_ID_UNIT, unitId)

export const getUnitList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_UNIT)

export const getUnitValue = (projectBook, unitValueId) =>
    getSheetRow(projectBook, constant.SHEET_ID_UNIT_VALUE, unitValueId)

export const getUnitValueList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_UNIT_VALUE)

export const getReference = (projectBook, referenceId) => {
    const content = getSheetRowWithKeyAndValue(projectBook, constant.SHEET_ID_REFERENCE, "citation_label", referenceId)
    return content ? content : getSheetRow(projectBook, constant.SHEET_ID_REFERENCE, referenceId)
}

export const getReferenceList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_REFERENCE)

export const getSoftware = (projectBook, softwareId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SOFTWARE, softwareId)

export const getSoftwareList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SOFTWARE)

export const getTerm = (projectBook, termId) =>
    getSheetRow(projectBook, constant.SHEET_ID_TERM, termId)

export const getTermList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_TERM)

export const getType = (projectBook, typeId) =>
    getSheetRow(projectBook, constant.SHEET_ID_TYPE, typeId)

export const getTypeList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_TYPE)

export const getSupplementaryFile = (projectBook, supplementaryFileId) =>
    getSheetRow(projectBook, constant.SHEET_ID_SUPPLEMENTARY_FILE, supplementaryFileId)

export const getSupplementaryFileList = projectBook =>
    getSheetRows(projectBook, constant.SHEET_ID_SUPPLEMENTARY_FILE)
