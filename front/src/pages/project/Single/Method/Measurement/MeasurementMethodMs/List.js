import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import MeasurementMethodMsList from '../../../../components/List/MeasurementMethodMs'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const measurementMethodMsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodMsList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <MeasurementMethodMsList measurementMethodMsList={measurementMethodMsList}/>
        </Widget>
    )
}

export default List