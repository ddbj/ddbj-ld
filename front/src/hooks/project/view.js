import {useMemo} from 'react'

import {useProjectBook} from './projectBook'

import * as viewService from '../../services/projectBook/view'

export const useProject = id => {
    const projectBook = useProjectBook(id)

    const project = useMemo(() => {
        if (!projectBook) return null
        return viewService.getProject(projectBook)
    }, [projectBook])

    return project
}

export const useExperimentList = id => {
    const projectBook = useProjectBook(id)

    const experimentList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExperimentList(projectBook)
    }, [projectBook])

    return experimentList
}

export const useExperiment = (id, experimentId) => {
    const projectBook = useProjectBook(id)

    const experiment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExperiment(projectBook, experimentId)
    }, [projectBook, experimentId])

    return experiment
}

export const useExperimentalDesignList = id => {
    const projectBook = useProjectBook(id)

    const experimentalDesignList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExperimentalDesignList(projectBook)
    }, [projectBook])

    return experimentalDesignList
}

export const useExperimentalDesign = (id, experimentalDesignId) => {
    const projectBook = useProjectBook(id)

    const experimentalDesign = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExperimentalDesign(projectBook, experimentalDesignId)
    }, [projectBook, experimentalDesignId])

    return experimentalDesign
}

export const useSamplePrepList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepList(projectBook)
    }, [projectBook])

    return samplePrepList
}

export const useSamplePrep = (id, samplePrepId) => {
    const projectBook = useProjectBook(id)

    const samplePrep = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrep(projectBook, samplePrepId)
    }, [projectBook, samplePrepId])

    return samplePrep
}

export const useSamplePrepAnimalList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepAnimalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepAnimalList(projectBook)
    }, [projectBook])

    return samplePrepAnimalList
}

export const useSamplePrepAnimal = (id, samplePrepAnimalId) => {
    const projectBook = useProjectBook(id)

    const samplePrepAnimal = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepAnimal(projectBook, samplePrepAnimalId)
    }, [projectBook, samplePrepAnimalId])

    return samplePrepAnimal
}

export const useSamplePrepPlantList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepPlantList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepPlantList(projectBook)
    }, [projectBook])

    return samplePrepPlantList
}

export const useSamplePrepPlant = (id, samplePrepPlantId) => {
    const projectBook = useProjectBook(id)

    const samplePrepPlant = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepPlant(projectBook, samplePrepPlantId)
    }, [projectBook, samplePrepPlantId])

    return samplePrepPlant
}

export const useSamplePrepChemicalList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepChemicalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepChemicalList(projectBook)
    }, [projectBook])

    return samplePrepChemicalList
}

export const useSamplePrepChemical = (id, samplePrepChemicalId) => {
    const projectBook = useProjectBook(id)

    const samplePrepChemical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepChemical(projectBook, samplePrepChemicalId)
    }, [projectBook, samplePrepChemicalId])

    return samplePrepChemical
}

export const useSamplePrepOtherList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepOtherList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepOtherList(projectBook)
    }, [projectBook])

    return samplePrepOtherList
}

export const useSamplePrepOther = (id, samplePrepOtherId) => {
    const projectBook = useProjectBook(id)

    const samplePrepOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepOther(projectBook, samplePrepOtherId)
    }, [projectBook, samplePrepOtherId])

    return samplePrepOther
}

export const useLightConditionList = id => {
    const projectBook = useProjectBook(id)

    const lightConditionList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getLightConditionList(projectBook)
    }, [projectBook])

    return lightConditionList
}

export const useLightCondition = (id, lightConditionId) => {
    const projectBook = useProjectBook(id)

    const lightCondition = useMemo(() => {
        if (!projectBook) return null
        return viewService.getLightCondition(projectBook, lightConditionId)
    }, [projectBook, lightConditionId])

    return lightCondition
}

export const useSamplePrepClinicalList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepClinicalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepClinicalList(projectBook)
    }, [projectBook])

    return samplePrepClinicalList
}

export const useSamplePrepClinical = (id, samplePrepClinicalId) => {
    const projectBook = useProjectBook(id)

    const samplePrepClinical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepClinical(projectBook, samplePrepClinicalId)
    }, [projectBook, samplePrepClinicalId])

    return samplePrepClinical
}

export const useSamplePrepBacteriaList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepBacteriaList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepBacteriaList(projectBook)
    }, [projectBook])

    return samplePrepBacteriaList
}

export const useSamplePrepBacteria = (id, samplePrepBacteriaId) => {
    const projectBook = useProjectBook(id)

    const samplePrepBacteria = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepBacteria(projectBook, samplePrepBacteriaId)
    }, [projectBook, samplePrepBacteriaId])

    return samplePrepBacteria
}

export const useSamplePrepEnvironmentList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepEnvironmentList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepEnvironmentList(projectBook)
    }, [projectBook])

    return samplePrepEnvironmentList
}

export const useSamplePrepEnvironment = (id, samplePrepEnvironmentId) => {
    const projectBook = useProjectBook(id)

    const samplePrepEnvironment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepEnvironment(projectBook, samplePrepEnvironmentId)
    }, [projectBook, samplePrepEnvironmentId])

    return samplePrepEnvironment
}

export const useSamplePrepFoodList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepFoodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepFoodList(projectBook)
    }, [projectBook])

    return samplePrepFoodList
}

export const useSamplePrepFood = (id, samplePrepFoodId) => {
    const projectBook = useProjectBook(id)

    const samplePrepFood = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepFood(projectBook, samplePrepFoodId)
    }, [projectBook, samplePrepFoodId])

    return samplePrepFood
}

export const useSamplePrepControlList = id => {
    const projectBook = useProjectBook(id)

    const samplePrepControlList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepControlList(projectBook)
    }, [projectBook])

    return samplePrepControlList
}

export const useSamplePrepControl = (id, samplePrepControlId) => {
    const projectBook = useProjectBook(id)

    const samplePrepControl = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepControl(projectBook, samplePrepControlId)
    }, [projectBook, samplePrepControlId])

    return samplePrepControl
}

export const useSampleList = id => {
    const projectBook = useProjectBook(id)

    const sampleList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleList(projectBook)
    }, [projectBook])

    return sampleList
}

export const useSample = (id, sampleId) => {
    const projectBook = useProjectBook(id)

    const sample = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSample(projectBook, sampleId)
    }, [projectBook, sampleId])

    return sample
}

export const useSampleAnimalList = id => {
    const projectBook = useProjectBook(id)

    const sampleAnimalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleAnimalList(projectBook)
    }, [projectBook])

    return sampleAnimalList
}

export const useSampleAnimal = (id, sampleAnimalId) => {
    const projectBook = useProjectBook(id)

    const sampleAnimal = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleAnimal(projectBook, sampleAnimalId)
    }, [projectBook, sampleAnimalId])

    return sampleAnimal
}

export const useSamplePlantList = id => {
    const projectBook = useProjectBook(id)

    const samplePlantList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePlantList(projectBook)
    }, [projectBook])

    return samplePlantList
}

export const useSamplePlant = (id, samplePlantId) => {
    const projectBook = useProjectBook(id)

    const samplePlant = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePlant(projectBook, samplePlantId)
    }, [projectBook, samplePlantId])

    return samplePlant
}

export const useSampleChemicalList = id => {
    const projectBook = useProjectBook(id)

    const sampleChemicalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleChemicalList(projectBook)
    }, [projectBook])

    return sampleChemicalList
}

export const useSampleChemical = (id, sampleChemicalId) => {
    const projectBook = useProjectBook(id)

    const sampleChemical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleChemical(projectBook, sampleChemicalId)
    }, [projectBook, sampleChemicalId])

    return sampleChemical
}

export const useSampleOtherList = id => {
    const projectBook = useProjectBook(id)

    const sampleOtherList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleOtherList(projectBook)
    }, [projectBook])

    return sampleOtherList
}

export const useSampleOther = (id, sampleOtherId) => {
    const projectBook = useProjectBook(id)

    const sampleOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleOther(projectBook, sampleOtherId)
    }, [projectBook, sampleOtherId])

    return sampleOther
}

export const useSampleClinicalList = id => {
    const projectBook = useProjectBook(id)

    const sampleClinicalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleClinicalList(projectBook)
    }, [projectBook])

    return sampleClinicalList
}

export const useSampleClinical = (id, sampleClinicalId) => {
    const projectBook = useProjectBook(id)

    const sampleClinical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleClinical(projectBook, sampleClinicalId)
    }, [projectBook, sampleClinicalId])

    return sampleClinical
}

export const useSampleBacteriaList = id => {
    const projectBook = useProjectBook(id)

    const sampleBacteriaList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleBacteriaList(projectBook)
    }, [projectBook])

    return sampleBacteriaList
}

export const useSampleBacteria = (id, sampleBacteriaId) => {
    const projectBook = useProjectBook(id)

    const sampleBacteria = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleBacteria(projectBook, sampleBacteriaId)
    }, [projectBook, sampleBacteriaId])

    return sampleBacteria
}

export const useSampleEnvironmentList = id => {
    const projectBook = useProjectBook(id)

    const sampleEnvironmentList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleEnvironmentList(projectBook)
    }, [projectBook])

    return sampleEnvironmentList
}

export const useSampleEnvironment = (id, sampleEnvironmentId) => {
    const projectBook = useProjectBook(id)

    const sampleEnvironment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleEnvironment(projectBook, sampleEnvironmentId)
    }, [projectBook, sampleEnvironmentId])

    return sampleEnvironment
}

export const useSampleFoodList = id => {
    const projectBook = useProjectBook(id)

    const sampleFoodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleFoodList(projectBook)
    }, [projectBook])

    return sampleFoodList
}

export const useSampleFood = (id, sampleFoodId) => {
    const projectBook = useProjectBook(id)

    const sampleFood = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleFood(projectBook, sampleFoodId)
    }, [projectBook, sampleFoodId])

    return sampleFood
}

export const useSampleControlList = id => {
    const projectBook = useProjectBook(id)

    const sampleControlList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleControlList(projectBook)
    }, [projectBook])

    return sampleControlList
}

export const useSampleControl = (id, sampleControlId) => {
    const projectBook = useProjectBook(id)

    const sampleControl = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleControl(projectBook, sampleControlId)
    }, [projectBook, sampleControlId])

    return sampleControl
}

export const useTreatmentList = id => {
    const projectBook = useProjectBook(id)

    const treatmentList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getTreatmentList(projectBook)
    }, [projectBook])

    return treatmentList
}

export const useTreatment = (id, treatmentId) => {
    const projectBook = useProjectBook(id)

    const treatment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getTreatment(projectBook, treatmentId)
    }, [projectBook, treatmentId])

    return treatment
}

export const useExtractionMethodList = id => {
    const projectBook = useProjectBook(id)

    const extractionMethodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExtractionMethodList(projectBook)
    }, [projectBook])

    return extractionMethodList
}

export const useExtractionMethod = (id, extractionMethodId) => {
    const projectBook = useProjectBook(id)

    const extractionMethod = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExtractionMethod(projectBook, extractionMethodId)
    }, [projectBook, extractionMethodId])

    return extractionMethod
}

export const useMeasurementMethodList = id => {
    const projectBook = useProjectBook(id)

    const measurementMethodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodList(projectBook)
    }, [projectBook])

    return measurementMethodList
}

export const useMeasurementMethod = (id, measurementMethodId) => {
    const projectBook = useProjectBook(id)

    const measurementMethod = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethod(projectBook, measurementMethodId)
    }, [projectBook, measurementMethodId])

    return measurementMethod
}

export const useMeasurementMethodLcmsList = id => {
    const projectBook = useProjectBook(id)

    const measurementMethodLcmsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodLcmsList(projectBook)
    }, [projectBook])

    return measurementMethodLcmsList
}

export const useMeasurementMethodLcms = (id, measurementMethodLcmsId) => {
    const projectBook = useProjectBook(id)

    const measurementMethodLcms = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodLcms(projectBook, measurementMethodLcmsId)
    }, [projectBook, measurementMethodLcmsId])

    return measurementMethodLcms
}

export const useMeasurementMethodGcmsList = id => {
    const projectBook = useProjectBook(id)

    const measurementMethodGcmsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodGcmsList(projectBook)
    }, [projectBook])

    return measurementMethodGcmsList
}

export const useMeasurementMethodGcms = (id, measurementMethodGcmsId) => {
    const projectBook = useProjectBook(id)

    const measurementMethodGcms = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodGcms(projectBook, measurementMethodGcmsId)
    }, [projectBook, measurementMethodGcmsId])

    return measurementMethodGcms
}

export const useMeasurementMethodCemsList = id => {
    const projectBook = useProjectBook(id)

    const measurementMethodCemsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodCemsList(projectBook)
    }, [projectBook])

    return measurementMethodCemsList
}

export const useMeasurementMethodCems = (id, measurementMethodCemsId) => {
    const projectBook = useProjectBook(id)

    const measurementMethodCems = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodCems(projectBook, measurementMethodCemsId)
    }, [projectBook, measurementMethodCemsId])

    return measurementMethodCems
}

export const useMeasurementMethodMsList = id => {
    const projectBook = useProjectBook(id)

    const measurementMethodMsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodMsList(projectBook)
    }, [projectBook])

    return measurementMethodMsList
}

export const useMeasurementMethodMs = (id, measurementMethodMsId) => {
    const projectBook = useProjectBook(id)

    const measurementMethodMs = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodMs(projectBook, measurementMethodMsId)
    }, [projectBook, measurementMethodMsId])

    return measurementMethodMs
}

export const useMeasurementMethodNmrList = id => {
    const projectBook = useProjectBook(id)

    const measurementMethodNmrList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodNmrList(projectBook)
    }, [projectBook])

    return measurementMethodNmrList
}

export const useMeasurementMethodNmr = (id, measurementMethodNmrId) => {
    const projectBook = useProjectBook(id)

    const measurementMethodNmr = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodNmr(projectBook, measurementMethodNmrId)
    }, [projectBook, measurementMethodNmrId])

    return measurementMethodNmr
}

export const useMeasurementMethodOtherList = id => {
    const projectBook = useProjectBook(id)

    const measurementMethodOtherList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodOtherList(projectBook)
    }, [projectBook])

    return measurementMethodOtherList
}

export const useMeasurementMethodOther = (id, measurementMethodOtherId) => {
    const projectBook = useProjectBook(id)

    const measurementMethodOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodOther(projectBook, measurementMethodOtherId)
    }, [projectBook, measurementMethodOtherId])

    return measurementMethodOther
}

export const useConditionLcList = id => {
    const projectBook = useProjectBook(id)

    const conditionLcList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionLcList(projectBook)
    }, [projectBook])

    return conditionLcList
}

export const useConditionLc = (id, conditionLcId) => {
    const projectBook = useProjectBook(id)

    const conditionLc = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionLc(projectBook, conditionLcId)
    }, [projectBook, conditionLcId])

    return conditionLc
}

export const useConditionGcList = id => {
    const projectBook = useProjectBook(id)

    const conditionGcList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionGcList(projectBook)
    }, [projectBook])

    return conditionGcList
}

export const useConditionGc = (id, conditionGcId) => {
    const projectBook = useProjectBook(id)

    const conditionGc = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionGc(projectBook, conditionGcId)
    }, [projectBook, conditionGcId])

    return conditionGc
}

export const useConditionCeList = id => {
    const projectBook = useProjectBook(id)

    const conditionCeList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionCeList(projectBook)
    }, [projectBook])

    return conditionCeList
}

export const useConditionCe = (id, conditionCeId) => {
    const projectBook = useProjectBook(id)

    const conditionCe = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionCe(projectBook, conditionCeId)
    }, [projectBook, conditionCeId])

    return conditionCe
}

export const useConditionMsList = id => {
    const projectBook = useProjectBook(id)

    const conditionMsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionMsList(projectBook)
    }, [projectBook])

    return conditionMsList
}

export const useConditionMs = (id, conditionMsId) => {
    const projectBook = useProjectBook(id)

    const conditionMs = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionMs(projectBook, conditionMsId)
    }, [projectBook, conditionMsId])

    return conditionMs
}

export const useConditionNmrList = id => {
    const projectBook = useProjectBook(id)

    const conditionNmrList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionNmrList(projectBook)
    }, [projectBook])

    return conditionNmrList
}

export const useConditionNmr = (id, conditionNmrId) => {
    const projectBook = useProjectBook(id)

    const conditionNmr = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionNmr(projectBook, conditionNmrId)
    }, [projectBook, conditionNmrId])

    return conditionNmr
}

export const useConditionOtherList = id => {
    const projectBook = useProjectBook(id)

    const conditionOtherList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionOtherList(projectBook)
    }, [projectBook])

    return conditionOtherList
}

export const useConditionOther = (id, conditionOtherId) => {
    const projectBook = useProjectBook(id)

    const conditionOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionOther(projectBook, conditionOtherId)
    }, [projectBook, conditionOtherId])

    return conditionOther
}

export const useInstrumentList = id => {
    const projectBook = useProjectBook(id)

    const instrumentList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getInstrumentList(projectBook)
    }, [projectBook])

    return instrumentList
}

export const useInstrument = (id, instrumentId) => {
    const projectBook = useProjectBook(id)

    const instrument = useMemo(() => {
        if (!projectBook) return null
        return viewService.getInstrument(projectBook, instrumentId)
    }, [projectBook, instrumentId])

    return instrument
}

export const useColumnList = id => {
    const projectBook = useProjectBook(id)

    const columnList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getColumnList(projectBook)
    }, [projectBook])

    return columnList
}

export const useColumn = (id, columnId) => {
    const projectBook = useProjectBook(id)

    const column = useMemo(() => {
        if (!projectBook) return null
        return viewService.getColumn(projectBook, columnId)
    }, [projectBook, columnId])

    return column
}

export const useMeasurementList = id => {
    const projectBook = useProjectBook(id)

    const measurementList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementList(projectBook)
    }, [projectBook])

    return measurementList
}

export const useMeasurement = (id, measurementId) => {
    const projectBook = useProjectBook(id)

    const measurement = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurement(projectBook, measurementId)
    }, [projectBook, measurementId])

    return measurement
}

export const useRawDataFileList = id => {
    const projectBook = useProjectBook(id)

    const rawDataFileList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getRawDataFileList(projectBook)
    }, [projectBook])

    return rawDataFileList
}

export const useRawDataFile = (id, rawDataFileId) => {
    const projectBook = useProjectBook(id)

    const rawDataFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getRawDataFile(projectBook, rawDataFileId)
    }, [projectBook, rawDataFileId])

    return rawDataFile
}

export const useDataAnalysisList = id => {
    const projectBook = useProjectBook(id)

    const dataAnalysisList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataAnalysisList(projectBook)
    }, [projectBook])

    return dataAnalysisList
}

export const useDataAnalysis = (id, dataAnalysisId) => {
    const projectBook = useProjectBook(id)

    const dataAnalysis = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataAnalysis(projectBook, dataAnalysisId)
    }, [projectBook, dataAnalysisId])

    return dataAnalysis
}

export const useAnalyzedRawFileList = id => {
    const projectBook = useProjectBook(id)

    const analyzedRawFileList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnalyzedRawFileList(projectBook)
    }, [projectBook])

    return analyzedRawFileList
}

export const useAnalyzedRawFile = (id, analyzedRawFileId) => {
    const projectBook = useProjectBook(id)

    const analyzedRawFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnalyzedRawFile(projectBook, analyzedRawFileId)
    }, [projectBook, analyzedRawFileId])

    return analyzedRawFile
}

export const useDataPreprocessingList = id => {
    const projectBook = useProjectBook(id)

    const dataPreprocessingList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataPreprocessingList(projectBook)
    }, [projectBook])

    return dataPreprocessingList
}

export const useDataPreprocessing = (id, dataPreprocessingId) => {
    const projectBook = useProjectBook(id)

    const dataPreprocessing = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataPreprocessing(projectBook, dataPreprocessingId)
    }, [projectBook, dataPreprocessingId])

    return dataPreprocessing
}

export const useDataProcessingMethodList = id => {
    const projectBook = useProjectBook(id)

    const dataProcessingMethodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethodList(projectBook)
    }, [projectBook])

    return dataProcessingMethodList
}

export const useDataProcessingMethod = (id, dataProcessingMethodId) => {
    const projectBook = useProjectBook(id)

    const dataProcessingMethod = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethod(projectBook, dataProcessingMethodId)
    }, [projectBook, dataProcessingMethodId])

    return dataProcessingMethod
}

export const useDataProcessingMethodTypeList = id => {
    const projectBook = useProjectBook(id)

    const dataProcessingMethodTypeList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethodTypeList(projectBook)
    }, [projectBook])

    return dataProcessingMethodTypeList
}

export const useDataProcessingMethodType = (id, dataProcessingMethodTypeId) => {
    const projectBook = useProjectBook(id)

    const dataProcessingMethodType = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethodType(projectBook, dataProcessingMethodTypeId)
    }, [projectBook, dataProcessingMethodTypeId])

    return dataProcessingMethodType
}

export const useAnnotationMethodList = id => {
    const projectBook = useProjectBook(id)

    const annotationMethodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnnotationMethodList(projectBook)
    }, [projectBook])

    return annotationMethodList
}

export const useAnnotationMethod = (id, annotationMethodId) => {
    const projectBook = useProjectBook(id)

    const annotationMethod = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnnotationMethod(projectBook, annotationMethodId)
    }, [projectBook, annotationMethodId])

    return annotationMethod
}

export const useAnalyzedResultFileList = id => {
    const projectBook = useProjectBook(id)

    const analyzedResultFileList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnalyzedResultFileList(projectBook)
    }, [projectBook])

    return analyzedResultFileList
}

export const useAnalyzedResultFile = (id, analyzedResultFileId) => {
    const projectBook = useProjectBook(id)

    const analyzedResultFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnalyzedResultFile(projectBook, analyzedResultFileId)
    }, [projectBook, analyzedResultFileId])

    return analyzedResultFile
}

export const usePersonList = id => {
    const projectBook = useProjectBook(id)

    const personList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getPersonList(projectBook)
    }, [projectBook])

    return personList
}

export const usePerson = (id, personId) => {
    const projectBook = useProjectBook(id)

    const person = useMemo(() => {
        if (!projectBook) return null
        return viewService.getPerson(projectBook, personId)
    }, [projectBook, personId])

    return person
}

export const useOrganisationList = id => {
    const projectBook = useProjectBook(id)

    const organisationList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getOrganisationList(projectBook)
    }, [projectBook])

    return organisationList
}

export const useOrganisation = (id, organisationId) => {
    const projectBook = useProjectBook(id)

    const organisation = useMemo(() => {
        if (!projectBook) return null
        return viewService.getOrganisation(projectBook, organisationId)
    }, [projectBook, organisationId])

    return organisation
}

export const useFileList = id => {
    const projectBook = useProjectBook(id)

    const fileList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getFileList(projectBook)
    }, [projectBook])

    return fileList
}

export const useFile = (id, fileId) => {
    const projectBook = useProjectBook(id)

    const file = useMemo(() => {
        if (!projectBook) return null
        return viewService.getFile(projectBook, fileId)
    }, [projectBook, fileId])

    return file
}

export const useFileFormatList = id => {
    const projectBook = useProjectBook(id)

    const fileFormatList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getFileFormatList(projectBook)
    }, [projectBook])

    return fileFormatList
}

export const useFileFormat = (id, fileFormatId) => {
    const projectBook = useProjectBook(id)

    const fileFormat = useMemo(() => {
        if (!projectBook) return null
        return viewService.getFileFormat(projectBook, fileFormatId)
    }, [projectBook, fileFormatId])

    return fileFormat
}

export const useUnitList = id => {
    const projectBook = useProjectBook(id)

    const unitList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getUnitList(projectBook)
    }, [projectBook])

    return unitList
}

export const useUnit = (id, unitId) => {
    const projectBook = useProjectBook(id)

    const unit = useMemo(() => {
        if (!projectBook) return null
        return viewService.getUnit(projectBook, unitId)
    }, [projectBook, unitId])

    return unit
}

export const useUnitValueList = id => {
    const projectBook = useProjectBook(id)

    const unitValueList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getUnitValueList(projectBook)
    }, [projectBook])

    return unitValueList
}

export const useUnitValue = (id, unitValueId) => {
    const projectBook = useProjectBook(id)

    const unitValue = useMemo(() => {
        if (!projectBook) return null
        return viewService.getUnitValue(projectBook, unitValueId)
    }, [projectBook, unitValueId])

    return unitValue
}

export const useReferenceList = id => {
    const projectBook = useProjectBook(id)

    const referenceList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getReferenceList(projectBook)
    }, [projectBook])

    return referenceList
}

export const useReference = (id, referenceId) => {
    const projectBook = useProjectBook(id)

    const reference = useMemo(() => {
        if (!projectBook) return null
        return viewService.getReference(projectBook, referenceId)
    }, [projectBook, referenceId])

    return reference
}

export const useSoftwareList = id => {
    const projectBook = useProjectBook(id)

    const softwareList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSoftwareList(projectBook)
    }, [projectBook])

    return softwareList
}

export const useSoftware = (id, softwareId) => {
    const projectBook = useProjectBook(id)

    const software = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSoftware(projectBook, softwareId)
    }, [projectBook, softwareId])

    return software
}

export const useTermList = id => {
    const projectBook = useProjectBook(id)

    const termList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getTermList(projectBook)
    }, [projectBook])

    return termList
}

export const useTerm = (id, termId) => {
    const projectBook = useProjectBook(id)

    const term = useMemo(() => {
        if (!projectBook) return null
        return viewService.getTerm(projectBook, termId)
    }, [projectBook, termId])

    return term
}

export const useTypeList = id => {
    const projectBook = useProjectBook(id)

    const typeList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getTypeList(projectBook)
    }, [projectBook])

    return typeList
}

export const useType = (id, typeId) => {
    const projectBook = useProjectBook(id)

    const type = useMemo(() => {
        if (!projectBook) return null
        return viewService.getType(projectBook, typeId)
    }, [projectBook, typeId])

    return type
}

export const useSupplementaryFileList = id => {
    const projectBook = useProjectBook(id)

    const supplementaryFileList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSupplementaryFileList(projectBook)
    }, [projectBook])

    return supplementaryFileList
}

export const useSupplementaryFile = (id, supplementaryFileId) => {
    const projectBook = useProjectBook(id)

    const supplementaryFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSupplementaryFile(projectBook, supplementaryFileId)
    }, [projectBook, supplementaryFileId])

    return supplementaryFile
}

