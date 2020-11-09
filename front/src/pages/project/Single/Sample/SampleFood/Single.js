import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleFoodSingle from '../../../components/Single/SampleFood'

const Single = ({match}) => {
    const {id, sampleFoodId} = match.params

    const projectBook = useProjectBook(id)
    const sampleFood = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleFood(projectBook, sampleFoodId)
    }, [projectBook, sampleFoodId])

    return (
        <Widget>
            <SampleFoodSingle sampleFood={sampleFood}/>
        </Widget>
    )
}

export default Single