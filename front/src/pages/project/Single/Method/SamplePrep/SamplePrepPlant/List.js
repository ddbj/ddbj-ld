import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepPlantList from '../../../../components/List/SamplePrepPlant'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepPlantList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepPlantList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepPlantList samplePrepPlantList={samplePrepPlantList}/>
        </Widget>
    )
}

export default List