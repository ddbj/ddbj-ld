import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionNmrSingle from '../../../../components/Single/ConditionNmr'

const Single = ({match}) => {
    const {id, conditionNmrId} = match.params

    const projectBook = useProjectBook(id)
    const conditionNmr = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionNmr(projectBook, conditionNmrId)
    }, [conditionNmrId, projectBook])

    return <ConditionNmrSingle conditionNmr={conditionNmr}/>
}

export default Single