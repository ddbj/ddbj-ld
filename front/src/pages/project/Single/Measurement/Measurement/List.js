import React, {useMemo} from 'react'

import * as customizedViewService from '../../../../../services/projectBook/view/customized'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import MeasurementList from '../../../components/List/Customized/Measurement'

const List = ({match}) => {
    const {id} = match.params

    const projectBoook = useProjectBook(id)
    const measurementList = useMemo(() => {
        if (!projectBoook) return null
        return customizedViewService.getMeasurementList(projectBoook)
    }, [projectBoook])

    if(!measurementList) {
        return null
    }

    return (
        <Widget>
            <MeasurementList measurementList={measurementList}/>
        </Widget>
    )
}

export default List