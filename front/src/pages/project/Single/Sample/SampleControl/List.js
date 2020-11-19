import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleControlList from '../../../components/List/SampleControl'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleControlList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleControlList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleControlList sampleControlList={sampleControlList}/>
        </Widget>
    )
}

export default List