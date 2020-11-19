import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionGcSingle from '../../../../components/Single/ConditionGc'

const Single = ({match}) => {
    const {id, conditionGcId} = match.params

    const projectBook = useProjectBook(id)
    const conditionGc = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionGc(projectBook, conditionGcId)
    }, [conditionGcId, projectBook])

    return <ConditionGcSingle conditionGc={conditionGc}/>
}

export default Single