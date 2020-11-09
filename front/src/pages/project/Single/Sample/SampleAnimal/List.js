import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleAnimalList from '../../../components/List/SampleAnimal'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleAnimalList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleAnimalList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleAnimalList sampleAnimalList={sampleAnimalList}/>
        </Widget>
    )
}

export default List