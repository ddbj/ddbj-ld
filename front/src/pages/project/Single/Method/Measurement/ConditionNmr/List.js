import React, {useMemo} from 'react'

import * as getResourceService from '../../../../../../services/projectBook/get/resource'
import * as getCustomizedService from '../../../../../../services/projectBook/get/customized'
import * as viewCoreService from '../../../../../../services/projectBook/view/core'
import * as viewParseService from '../../../../../../services/projectBook/view/parse'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionNmrList from '../../../../components/List/ConditionNmr'

const List = ({match}) => {
    const {id, measurementMethodType, measurementMethodId} = match.params

    const projectBook = useProjectBook(id)
    const conditionNmrList = useMemo(() => {
        if (!projectBook) return null
        const measrumentMethod = getCustomizedService.getMeasurementMethodByIdAndType(projectBook, measurementMethodId, measurementMethodType)
        const rows = (measrumentMethod.nmr_condition_id || []).map(conditionNmrId => getResourceService.getConditionNmr(projectBook, conditionNmrId))
        return viewCoreService.getDataList(projectBook, rows, viewParseService.parseConditionNmr)
    }, [measurementMethodId, measurementMethodType, projectBook])

    return <ConditionNmrList conditionNmrList={conditionNmrList}/>
}

export default List