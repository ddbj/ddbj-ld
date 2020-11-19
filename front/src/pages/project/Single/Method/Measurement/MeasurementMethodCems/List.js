import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import MeasurementMethodCemsList from '../../../../components/List/MeasurementMethodCems'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const measurementMethodCemsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodCemsList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <MeasurementMethodCemsList measurementMethodCemsList={measurementMethodCemsList}/>
        </Widget>
    )
}

export default List