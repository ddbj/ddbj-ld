import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepAnimalSingle from '../../../../components/Single/SamplePrepAnimal'

const Single = ({match}) => {
    const {id, samplePrepAnimalId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepAnimal = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepAnimal(projectBook, samplePrepAnimalId)
    }, [samplePrepAnimalId, projectBook])

    return (
        <Widget>
            <SamplePrepAnimalSingle samplePrepAnimal={samplePrepAnimal}/>
        </Widget>
    )
}

export default Single