import React, {useMemo} from 'react'

import * as customizedViewService from '../../../../../services/projectBook/view/customized'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import ExperimentList from '../../../components/List/Customized/Experiment'

const List = ({match}) => {
    const {id} = match.params
    const projectBook = useProjectBook(id)

    const experimentList = useMemo(() => {
        if (!projectBook || 0 === Object.keys(projectBook).length) return null
        return customizedViewService.getExperimentList(projectBook)
    }, [projectBook])

    if (!experimentList) {
        return null
    }

    return (
        <Widget>
            <ExperimentList experimentList={experimentList}/>
        </Widget>
    )
}

export default List