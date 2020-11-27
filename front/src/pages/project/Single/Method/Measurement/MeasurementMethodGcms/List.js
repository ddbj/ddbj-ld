import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import MeasurementMethodGcmsList from '../../../../components/List/MeasurementMethodGcms'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const measurementMethodGcmsList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodGcmsList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <MeasurementMethodGcmsList measurementMethodGcmsList={measurementMethodGcmsList}/>
        </Widget>
    )
}

export default List