import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import LightConditionList from '../../../components/List/LightCondition'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const lightConditionList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getLightConditionList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <LightConditionList lightConditionList={lightConditionList}/>
        </Widget>
    )
}

export default List