import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleBacteriaSingle from '../../../components/Single/SampleBacteria'

const Single = ({match}) => {
    const {id, sampleBacteriaId} = match.params

    const projectBook = useProjectBook(id)
    const sampleBacteria = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleBacteria(projectBook, sampleBacteriaId)
    }, [projectBook, sampleBacteriaId])

    return (
        <Widget>
            <SampleBacteriaSingle sampleBacteria={sampleBacteria}/>
        </Widget>
    )
}

export default Single