import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SamplePlantList from '../../../components/List/SamplePlant'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePlantList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSamplePlantList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePlantList samplePlantList={samplePlantList}/>
        </Widget>
    )
}

export default List