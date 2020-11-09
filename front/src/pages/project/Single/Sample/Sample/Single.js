import React, {useMemo} from 'react'
import {Redirect} from 'react-router-dom'

import * as getResourceService from '../../../../../services/projectBook/get/resource'
import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'
import {useBuildPath} from '../../../../../hooks/project/path'

import Widget from '../../../../../components/Widget/Widget'
import SampleSingle from '../../../components/Single/Sample'

const Single = ({match}) => {
    const {id, sampleId} = match.params
    const buildPath = useBuildPath()

    const projectBook = useProjectBook(id)
    const sample = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSample(projectBook, sampleId)
    }, [projectBook, sampleId])

    const redirectPath = useMemo(() => {
        if (!projectBook || !sample) return null
        if (getResourceService.getSampleAnimal(projectBook, sampleId)) return buildPath(id, `/sample/sample_animal/${sampleId}`)
        if (getResourceService.getSamplePlant(projectBook, sampleId)) return buildPath(id, `/sample/sample_plant/${sampleId}`)
        if (getResourceService.getSampleChemical(projectBook, sampleId)) return buildPath(id, `/sample/sample_chemical/${sampleId}`)
        if (getResourceService.getSampleOther(projectBook, sampleId)) return buildPath(id, `/sample/sample_other/${sampleId}`)
        if (getResourceService.getSampleClinical(projectBook, sampleId)) return buildPath(id, `/sample/sample_clinical/${sampleId}`)
        if (getResourceService.getSampleBacteria(projectBook, sampleId)) return buildPath(id, `/sample/sample_bacteria/${sampleId}`)
        if (getResourceService.getSampleEnvironment(projectBook, sampleId)) return buildPath(id, `/sample/sample_environment/${sampleId}`)
        if (getResourceService.getSampleFood(projectBook, sampleId)) return buildPath(id, `/sample/sample_food/${sampleId}`)
        if (getResourceService.getSampleControl(projectBook, sampleId)) return buildPath(id, `/sample/sample_control/${sampleId}`)
        return null
    }, [buildPath, id, projectBook, sample, sampleId])

    return redirectPath ? (
        <Redirect to={redirectPath}/>
    ) : (
        <Widget>
            <SampleSingle sample={sample}/>
        </Widget>
    )
}

export default Single