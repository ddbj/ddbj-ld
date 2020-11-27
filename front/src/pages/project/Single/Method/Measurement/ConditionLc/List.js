import React, {useMemo} from 'react'

import * as getResourceService from '../../../../../../services/projectBook/get/resource'
import * as getCustomizedService from '../../../../../../services/projectBook/get/customized'
import * as viewCoreService from '../../../../../../services/projectBook/view/core'
import * as viewParseService from '../../../../../../services/projectBook/view/parse'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import ConditionLcList from '../../../../components/List/ConditionLc'

const List = ({match}) => {
    const {id, measurementMethodType, measurementMethodId} = match.params

    const projectBook = useProjectBook(id)
    const conditionLcList = useMemo(() => {
        if (!projectBook) return null
        const measrumentMethod = getCustomizedService.getMeasurementMethodByIdAndType(projectBook, measurementMethodId, measurementMethodType)
        const rows = (measrumentMethod.lc_condition_id || []).map(conditionLcId => getResourceService.getConditionLc(projectBook, conditionLcId))
        return viewCoreService.getDataList(projectBook, rows, viewParseService.parseConditionLc)
    }, [measurementMethodId, measurementMethodType, projectBook])

    return <ConditionLcList conditionLcList={conditionLcList}/>
}

export default List