import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepFoodList from '../../../../components/List/SamplePrepFood'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepFoodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepFoodList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepFoodList samplePrepFoodList={samplePrepFoodList}/>
        </Widget>
    )
}

export default List