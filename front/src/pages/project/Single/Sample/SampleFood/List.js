import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleFoodList from '../../../components/List/SampleFood'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleFoodList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleFoodList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleFoodList sampleFoodList={sampleFoodList}/>
        </Widget>
    )
}

export default List