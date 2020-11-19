import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleEnvironmentSingle from '../../../components/Single/SampleEnvironment'

const Single = ({match}) => {
    const {id, sampleEnvironmentId} = match.params

    const projectBook = useProjectBook(id)
    const sampleEnvironment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleEnvironment(projectBook, sampleEnvironmentId)
    }, [projectBook, sampleEnvironmentId])

    return (
        <Widget>
            <SampleEnvironmentSingle sampleEnvironment={sampleEnvironment}/>
        </Widget>
    )
}

export default Single