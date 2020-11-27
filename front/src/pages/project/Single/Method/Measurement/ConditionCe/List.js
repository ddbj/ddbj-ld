import React, {useMemo} from 'react'

import * as getResourceService from '../../../../../../services/projectBook/get/resource'
import * as getCustomizedService from '../../../../../../services/projectBook/get/customized'
import * as viewCoreService from '../../../../../../services/projectBook/view/core'
import * as viewParseService from '../../../../../../services/projectBook/view/parse'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionCeList from '../../../../components/List/ConditionCe'

const List = ({match}) => {
    const {id, measurementMethodType, measurementMethodId} = match.params

    const projectBook = useProjectBook(id)
    const conditionCeList = useMemo(() => {
        if (!projectBook) return null
        const measrumentMethod = getCustomizedService.getMeasurementMethodByIdAndType(projectBook, measurementMethodId, measurementMethodType)
        const rows = (measrumentMethod.ce_condition_id || []).map(conditionCeId => getResourceService.getConditionCe(projectBook, conditionCeId))
        return viewCoreService.getDataList(projectBook, rows, viewParseService.parseConditionCe)
    }, [measurementMethodId, measurementMethodType, projectBook])

    return <ConditionCeList conditionCeList={conditionCeList}/>
}

export default List