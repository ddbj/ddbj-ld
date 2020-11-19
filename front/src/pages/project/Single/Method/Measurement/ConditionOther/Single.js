import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionOtherSingle from '../../../../components/Single/ConditionOther'

const Single = ({match}) => {
    const {id, conditionOtherId} = match.params

    const projectBook = useProjectBook(id)
    const conditionOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getConditionOther(projectBook, conditionOtherId)
    }, [conditionOtherId, projectBook])

    return <ConditionOtherSingle conditionOther={conditionOther}/>
}

export default Single