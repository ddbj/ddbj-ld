import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepEnvironmentSingle from '../../../../components/Single/SamplePrepEnvironment'

const Single = ({match}) => {
    const {id, samplePrepEnvironmentId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepEnvironment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepEnvironment(projectBook, samplePrepEnvironmentId)
    }, [samplePrepEnvironmentId, projectBook])

    return (
        <Widget>
            <SamplePrepEnvironmentSingle samplePrepEnvironment={samplePrepEnvironment}/>
        </Widget>
    )
}

export default Single