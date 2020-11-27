import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepEnvironmentList from '../../../../components/List/SamplePrepEnvironment'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepEnvironmentList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepEnvironmentList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepEnvironmentList samplePrepEnvironmentList={samplePrepEnvironmentList}/>
        </Widget>
    )
}

export default List