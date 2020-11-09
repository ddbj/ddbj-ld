import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepFoodSingle from '../../../../components/Single/SamplePrepFood'

const Single = ({match}) => {
    const {id, samplePrepFoodId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepFood = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepFood(projectBook, samplePrepFoodId)
    }, [samplePrepFoodId, projectBook])

    return (
        <Widget>
            <SamplePrepFoodSingle samplePrepFood={samplePrepFood}/>
        </Widget>
    )
}

export default Single