import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionCeSingle from '../../../../components/Single/ConditionCe'

const Single = ({match}) => {
    const {id, conditionCeId} = match.params

    const projectBook = useProjectBook(id)
    const conditionCe = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionCe(projectBook, conditionCeId)
    }, [conditionCeId, projectBook])

    return <ConditionCeSingle conditionCe={conditionCe}/>
}

export default Single