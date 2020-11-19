import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepPlantSingle from '../../../../components/Single/SamplePrepPlant'

const Single = ({match}) => {
    const {id, samplePrepPlantId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepPlant = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepPlant(projectBook, samplePrepPlantId)
    }, [samplePrepPlantId, projectBook])

    return (
        <Widget>
            <SamplePrepPlantSingle samplePrepPlant={samplePrepPlant}/>
        </Widget>
    )
}

export default Single