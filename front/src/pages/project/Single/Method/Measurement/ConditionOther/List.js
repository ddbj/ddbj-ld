import React, {useMemo} from 'react'

import * as getResourceService from '../../../../../../services/projectBook/get/resource'
import * as getCustomizedService from '../../../../../../services/projectBook/get/customized'
import * as viewCoreService from '../../../../../../services/projectBook/view/core'
import * as viewParseService from '../../../../../../services/projectBook/view/parse'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionOtherList from '../../../../components/List/ConditionOther'

const List = ({match}) => {
    const {id, measurementMethodType, measurementMethodId} = match.params

    const projectBook = useProjectBook(id)
    const conditionOtherList = useMemo(() => {
        if (!projectBook) return null
        const measrumentMethod = getCustomizedService.getMeasurementMethodByIdAndType(projectBook, measurementMethodId, measurementMethodType)
        const rows = (measrumentMethod.other_condition_id || []).map(conditionOtherId => getResourceService.getConditionOther(projectBook, conditionOtherId))
        return viewCoreService.getDataList(projectBook, rows, viewParseService.parseConditionOther)
    }, [measurementMethodId, measurementMethodType, projectBook])

    return <ConditionOtherList conditionOtherList={conditionOtherList}/>
}

export default List