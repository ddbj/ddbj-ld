import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepBacteriaList from '../../../../components/List/SamplePrepBacteria'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepBacteriaList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepBacteriaList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepBacteriaList samplePrepBacteriaList={samplePrepBacteriaList}/>
        </Widget>
    )
}

export default List