import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepControlList from '../../../../components/List/SamplePrepControl'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepControlList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepControlList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepControlList samplePrepControlList={samplePrepControlList}/>
        </Widget>
    )
}

export default List