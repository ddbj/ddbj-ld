import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleList from '../../../components/List/Sample'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleList = useMemo(() => {
        if (!projectBook || 0 === Object.keys(projectBook).length) return null
        return viewService.getSampleList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleList sampleList={sampleList}/>
        </Widget>
    )
}

export default List