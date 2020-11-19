import React, {useCallback} from 'react'
import {Redirect} from 'react-router-dom'

import {useBuildPath} from '../../../../hooks/project/path'

const useBuildViewPath = () => {
    const buildPath = useBuildPath()

    const buildViewPath = useCallback((projectId, resourceName, resourceId) => {
        switch (resourceName) {
            case 'project':
                return buildPath(projectId, `/about/`)
            case 'person':
                return buildPath(projectId, `/about/person/${resourceId}`)
            case 'reference':
                return buildPath(projectId, `/about/reference/${resourceId}`)
            case 'experiment':
                return buildPath(projectId, `/experiment/experiment/${resourceId}`)
            case 'experimental_design':
                return buildPath(projectId, `/experiment/experimental_design/${resourceId}`)
            case 'analyzed_result_file':
                return buildPath(projectId, `/file/analyzed_result_file/${resourceId}`)
            case 'raw_data_file':
                return buildPath(projectId, `/file/raw_data_file/${resourceId}`)
            case 'supplementary_file':
                return buildPath(projectId, `/file/supplementary_file/${resourceId}`)
            case 'analyzed_raw_file':
                return buildPath(projectId, `/measurement/analyzed_raw_file/${resourceId}`)
            case 'measurement':
                return buildPath(projectId, `/measurement/measurement/${resourceId}`)
            // case 'analyzed_raw_file':
            //   return buildPath(projectId, `/measurement/raw_data_file/${resourceId}`)
            case 'extraction_method':
                return buildPath(projectId, `/method/measurement/extraction_method/${resourceId}`)
            case 'measurement_method_lcms':
                return buildPath(projectId, `/method/measurement/measurement_method_lcms/${resourceId}`)
            case 'measurement_method_gcms':
                return buildPath(projectId, `/method/measurement/measurement_method_gcms/${resourceId}`)
            case 'measurement_method_cems':
                return buildPath(projectId, `/method/measurement/measurement_method_cems/${resourceId}`)
            case 'measurement_method_ms':
                return buildPath(projectId, `/method/measurement/measurement_method_ms/${resourceId}`)
            case 'measurement_method_nmr':
                return buildPath(projectId, `/method/measurement/measurement_method_nmr/${resourceId}`)
            case 'measurement_method_other':
                return buildPath(projectId, `/method/measurement/measurement_method_other/${resourceId}`)
            case 'instrument':
                return buildPath(projectId, `/method/measurement/instrument/${resourceId}`)
            case 'column':
                return buildPath(projectId, `/method/measurement/column/${resourceId}`)
            case 'condition_lc':
                return buildPath(projectId, `/method/measurement/condition_lc/${resourceId}`)
            case 'condition_gc':
                return buildPath(projectId, `/method/measurement/condition_gc/${resourceId}`)
            case 'condition_ce':
                return buildPath(projectId, `/method/measurement/condition_ce/${resourceId}`)
            case 'condition_ms':
                return buildPath(projectId, `/method/measurement/condition_ms/${resourceId}`)
            case 'condition_nmr':
                return buildPath(projectId, `/method/measurement/condition_nmr/${resourceId}`)
            case 'condition_other':
                return buildPath(projectId, `/method/measurement/condition_other/${resourceId}`)
            case 'sample_prep':
                return buildPath(projectId, `/method/sample_prep/sample_prep/${resourceId}`)
            case 'sample_prep_animal':
                return buildPath(projectId, `/method/sample_prep/sample_prep_animal/${resourceId}`)
            case 'sample_prep_plant':
                return buildPath(projectId, `/method/sample_prep/sample_prep_plant/${resourceId}`)
            case 'sample_prep_chemical':
                return buildPath(projectId, `/method/sample_prep/sample_prep_chemical/${resourceId}`)
            case 'sample_prep_other':
                return buildPath(projectId, `/method/sample_prep/sample_prep_other/${resourceId}`)
            case 'sample_prep_clinical':
                return buildPath(projectId, `/method/sample_prep/sample_prep_clinical/${resourceId}`)
            case 'sample_prep_bacteria':
                return buildPath(projectId, `/method/sample_prep/sample_prep_bacteria/${resourceId}`)
            case 'sample_prep_environment':
                return buildPath(projectId, `/method/sample_prep/sample_prep_environment/${resourceId}`)
            case 'sample_prep_food':
                return buildPath(projectId, `/method/sample_prep/sample_prep_food/${resourceId}`)
            case 'sample_prep_control':
                return buildPath(projectId, `/method/sample_prep/sample_prep_control/${resourceId}`)
            case 'annotation_method':
                return buildPath(projectId, `/method/analysis/annotation_method/${resourceId}`)
            case 'data_analysis':
                return buildPath(projectId, `/method/analysis/data_analysis/${resourceId}`)
            case 'data_preprocessing':
                return buildPath(projectId, `/method/analysis/data_preprocessing/${resourceId}`)
            case 'data_processing_method':
                return buildPath(projectId, `/method/analysis/data_processing_method/${resourceId}`)
            case 'software':
                return buildPath(projectId, `/method/software/${resourceId}`)
            case 'treatment':
                return buildPath(projectId, `/other/treatment/${resourceId}`)
            case 'light_condition':
                return buildPath(projectId, `/other/light_condition/${resourceId}`)
            case 'term':
                return buildPath(projectId, `/other/term/${resourceId}`)
            case 'data_processing_method_type':
                return buildPath(projectId, `/other/data_processing_method_type/${resourceId}`)
            case 'organisation':
                return buildPath(projectId, `/other/organisation/${resourceId}`)
            case 'sample':
                return buildPath(projectId, `/sample/sample/${resourceId}`)
            case 'sample_animal':
                return buildPath(projectId, `/sample/sample_animal/${resourceId}`)
            case 'sample_plant':
                return buildPath(projectId, `/sample/sample_plant/${resourceId}`)
            case 'sample_chemical':
                return buildPath(projectId, `/sample/sample_chemical/${resourceId}`)
            case 'sample_clinical':
                return buildPath(projectId, `/sample/sample_clinical/${resourceId}`)
            case 'sample_bacteria':
                return buildPath(projectId, `/sample/sample_bacteria/${resourceId}`)
            case 'sample_environment':
                return buildPath(projectId, `/sample/sample_environment/${resourceId}`)
            case 'sample_food':
                return buildPath(projectId, `/sample/sample_food/${resourceId}`)
            case 'sample_control':
                return buildPath(projectId, `/sample/sample_control/${resourceId}`)
            case 'sample_other':
                return buildPath(projectId, `/sample/sample_other/${resourceId}`)
            default:
                return null
        }
    }, [buildPath])

    return buildViewPath
}

// TODO: 例外対応
const Resource = ({location}) => {
    const {pathname} = location
    const buildViewPath = useBuildViewPath()

    const m = pathname.match(/^\/me\/project\/([\w-]+)(\/draft)?\/resource\/([\w-]+)\/([\w-]+)/)
    const p = pathname.match(/^\/project\/([\w-]+)(\/draft)?\/resource\/([\w-]+)\/([\w-]+)/)
    if (!m && !p) return <Redirect to="/"/>

    if(m) {
        const [, projectId, , resourceName, resourceId] = m
        const path = buildViewPath(projectId, resourceName, resourceId) || pathname
        return <Redirect to={path}/>
    }

    if(p) {
        const [, projectId, , resourceName, resourceId] = p
        const path = buildViewPath(projectId, resourceName, resourceId) || pathname
        return <Redirect to={path}/>
    }
}

export default Resource