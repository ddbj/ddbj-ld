import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import ExperimentalDesignSingle from '../../../components/Single/ExperimentalDesign'

const Single = ({match}) => {
    const {id, experimentalDesignId} = match.params
    const projectBook = useProjectBook(id)

    const experimentalDesign = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExperimentalDesign(projectBook, experimentalDesignId)
    }, [experimentalDesignId, projectBook])

    return (
        <Widget>
            <ExperimentalDesignSingle experimentalDesign={experimentalDesign}/>
        </Widget>
    )
}

export default Single