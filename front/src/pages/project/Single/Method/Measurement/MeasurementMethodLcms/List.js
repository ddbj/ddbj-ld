import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import MeasurementMethodLcmsList from '../../../../components/List/MeasurementMethodLcms'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const measurementMethodLcmsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodLcmsList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <MeasurementMethodLcmsList measurementMethodLcmsList={measurementMethodLcmsList}/>
        </Widget>
    )
}

export default List