import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SamplePlantSingle from '../../../components/Single/SamplePlant'

const Single = ({match}) => {
    const {id, samplePlantId} = match.params

    const projectBook = useProjectBook(id)
    const samplePlant = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePlant(projectBook, samplePlantId)
    }, [projectBook, samplePlantId])

    return (
        <Widget>
            <SamplePlantSingle samplePlant={samplePlant}/>
        </Widget>
    )
}

export default Single