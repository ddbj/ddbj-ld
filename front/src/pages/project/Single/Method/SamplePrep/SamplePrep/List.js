import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepList from '../../../../components/List/SamplePrep'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepList(projectBook)
    }, [projectBook])

    if(!samplePrepList) {
        return null
    }

    return (
        <Widget>
            <SamplePrepList samplePrepList={samplePrepList}/>
        </Widget>
    )
}

export default List