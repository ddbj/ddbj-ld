import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepAnimalList from '../../../../components/List/SamplePrepAnimal'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepAnimalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepAnimalList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepAnimalList samplePrepAnimalList={samplePrepAnimalList}/>
        </Widget>
    )
}

export default List