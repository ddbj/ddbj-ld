import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleAnimalSingle from '../../../components/Single/SampleAnimal'

const Single = ({match}) => {
    const {id, sampleAnimalId} = match.params

    const projectBook = useProjectBook(id)
    const sampleAnimal = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleAnimal(projectBook, sampleAnimalId)
    }, [projectBook, sampleAnimalId])

    return (
        <Widget>
            <SampleAnimalSingle sampleAnimal={sampleAnimal}/>
        </Widget>
    )
}

export default Single