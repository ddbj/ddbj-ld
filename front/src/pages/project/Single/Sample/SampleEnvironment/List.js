import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleEnvironmentList from '../../../components/List/SampleEnvironment'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleEnvironmentList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleEnvironmentList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleEnvironmentList sampleEnvironmentList={sampleEnvironmentList}/>
        </Widget>
    )
}

export default List