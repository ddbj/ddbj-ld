import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import MeasurementMethodNmrList from '../../../../components/List/MeasurementMethodNmr'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const measurementMethodNmrList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodNmrList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <MeasurementMethodNmrList measurementMethodNmrList={measurementMethodNmrList}/>
        </Widget>
    )
}

export default List