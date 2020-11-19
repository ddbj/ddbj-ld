import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepBacteriaSingle from '../../../../components/Single/SamplePrepBacteria'

const Single = ({match}) => {
    const {id, samplePrepBacteriaId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepBacteria = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepBacteria(projectBook, samplePrepBacteriaId)
    }, [samplePrepBacteriaId, projectBook])

    return (
        <Widget>
            <SamplePrepBacteriaSingle samplePrepBacteria={samplePrepBacteria}/>
        </Widget>
    )
}

export default Single