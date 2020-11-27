import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import LightConditionSingle from '../../../components/Single/LightCondition'

const Single = ({match}) => {
    const {id, lightConditionId} = match.params

    const projectBook = useProjectBook(id)
    const lightCondition = useMemo(() => {
        if (!projectBook) return null
        return viewService.getLightCondition(projectBook, lightConditionId)
    }, [projectBook, lightConditionId])

    return (
        <Widget>
            <LightConditionSingle lightCondition={lightCondition}/>
        </Widget>
    )
}

export default Single