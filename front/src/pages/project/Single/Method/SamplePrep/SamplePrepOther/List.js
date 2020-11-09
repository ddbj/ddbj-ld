import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepOtherList from '../../../../components/List/SamplePrepOther'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepOtherList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepOtherList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepOtherList samplePrepOtherList={samplePrepOtherList}/>
        </Widget>
    )
}

export default List