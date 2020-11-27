import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionLcSingle from '../../../../components/Single/ConditionLc'

const Single = ({match}) => {
    const {id, conditionLcId} = match.params

    const projectBook = useProjectBook(id)
    const conditionLc = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionLc(projectBook, conditionLcId)
    }, [conditionLcId, projectBook])

    return <ConditionLcSingle conditionLc={conditionLc}/>
}

export default Single