import * as getResourceService from './resource'

export const getMeasurementMethodByIdAndType = (projectBook, measurementMethodId, measurementMethodType = null) => {
    switch (measurementMethodType) {
        case 'measurement_method_lcms':
            return getResourceService.getMeasurementMethodLcms(projectBook, measurementMethodId)
        case 'measurement_method_gcms':
            return getResourceService.getMeasurementMethodGcms(projectBook, measurementMethodId)
        case 'measurement_method_cems':
            return getResourceService.getMeasurementMethodCems(projectBook, measurementMethodId)
        case 'measurement_method_ms':
            return getResourceService.getMeasurementMethodMs(projectBook, measurementMethodId)
        case 'measurement_method_nmr':
            return getResourceService.getMeasurementMethodNmr(projectBook, measurementMethodId)
        case 'measurement_method_other':
            return getResourceService.getMeasurementMethodOther(projectBook, measurementMethodId)
        default:
            return getResourceService.getMeasurementMethod(projectBook, measurementMethodId)
    }
}