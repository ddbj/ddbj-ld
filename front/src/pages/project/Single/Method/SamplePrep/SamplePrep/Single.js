import React, {useMemo} from 'react'
import {Redirect} from 'react-router-dom'

import * as getResourceService from '../../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'
import {useBuildPath} from '../../../../../../hooks/project/path'

import Widget from '../../../../../../components/Widget/Widget'
import SamplePrepSingle from '../../../../components/Single/SamplePrep'

const Single = ({match}) => {
    const {id, samplePrepId} = match.params

    const buildPath = useBuildPath()
    const projectBook = useProjectBook(id)

    const samplePrepType = useMemo(() => {
        if (!projectBook) return null
        if (getResourceService.getSamplePrepAnimal(projectBook, samplePrepId)) return 'sample_prep_animal'
        if (getResourceService.getSamplePrepPlant(projectBook, samplePrepId)) return 'sample_prep_plant'
        if (getResourceService.getSamplePrepChemical(projectBook, samplePrepId)) return 'sample_prep_chemical'
        if (getResourceService.getSamplePrepOther(projectBook, samplePrepId)) return 'sample_prep_other'
        if (getResourceService.getSamplePrepClinical(projectBook, samplePrepId)) return 'sample_prep_clinical'
        if (getResourceService.getSamplePrepBacteria(projectBook, samplePrepId)) return 'sample_prep_bacteria'
        if (getResourceService.getSamplePrepEnvironment(projectBook, samplePrepId)) return 'sample_prep_environment'
        if (getResourceService.getSamplePrepFood(projectBook, samplePrepId)) return 'sample_prep_food'
        if (getResourceService.getSamplePrepControl(projectBook, samplePrepId)) return 'sample_prep_control'
        return null
    }, [projectBook, samplePrepId])

    const redirectPath = useMemo(() => {
        if (!samplePrepType || !samplePrepId) return null
        return buildPath(id, `/method/sample_prep/${samplePrepType}/${samplePrepId}`)
    }, [samplePrepType, samplePrepId, buildPath, id])

    if(!samplePrepType) {
        return null
    }

    return redirectPath ? (
        <Redirect to={redirectPath}/>
    ) : (
        <Widget>
            <SamplePrepSingle {...match.params} />
        </Widget>
    )
}

export default Single