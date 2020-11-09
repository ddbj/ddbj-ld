import React, {useMemo} from 'react'

import * as getResourceService from '../../../../../../services/projectBook/get/resource'
import * as getCustomizedService from '../../../../../../services/projectBook/get/customized'
import * as viewCoreService from '../../../../../../services/projectBook/view/core'
import * as viewParseService from '../../../../../../services/projectBook/view/parse'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionGcList from '../../../../components/List/ConditionGc'

const List = ({match}) => {
    const {id, measurementMethodType, measurementMethodId} = match.params

    const projectBook = useProjectBook(id)
    const conditionGcList = useMemo(() => {
        if (!projectBook) return null
        const measrumentMethod = getCustomizedService.getMeasurementMethodByIdAndType(projectBook, measurementMethodId, measurementMethodType)
        const rows = (measrumentMethod.gc_condition_id || []).map(conditionGcId => getResourceService.getConditionGc(projectBook, conditionGcId))
        return viewCoreService.getDataList(projectBook, rows, viewParseService.parseConditionGc)
    }, [measurementMethodId, measurementMethodType, projectBook])

    return <ConditionGcList conditionGcList={conditionGcList}/>
}

export default List