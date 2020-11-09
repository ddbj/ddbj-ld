import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import ExperimentSingle from '../../../components/Single/Experiment'

const Single = ({match}) => {
    const {id, experimentId} = match.params
    const projectBook = useProjectBook(id)

    const experiment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExperiment(projectBook, experimentId)
    }, [experimentId, projectBook])

    return (
        <Widget>
            <ExperimentSingle experiment={experiment}/>
        </Widget>
    )
}

export default Single