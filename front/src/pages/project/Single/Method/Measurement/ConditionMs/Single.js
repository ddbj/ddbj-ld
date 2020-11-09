import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionMsSingle from '../../../../components/Single/ConditionMs'

const Single = ({match}) => {
    const {id, conditionMsId} = match.params

    const projectBook = useProjectBook(id)
    const conditionMs = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionMs(projectBook, conditionMsId)
    }, [conditionMsId, projectBook])

    return <ConditionMsSingle conditionMs={conditionMs}/>
}

export default Single