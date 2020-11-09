import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleBacteriaList from '../../../components/List/SampleBacteria'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleBacteriaList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleBacteriaList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleBacteriaList sampleBacteriaList={sampleBacteriaList}/>
        </Widget>
    )
}

export default List