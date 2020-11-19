import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import MeasurementSingle from '../../../components/Single/Measurement'

const Single = ({match}) => {
    const {id, measurementId} = match.params

    const projectBook = useProjectBook(id)
    const measurement = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurement(projectBook, measurementId)
    }, [projectBook, measurementId])

    if(!measurement) {
        return null
    }

    return (
        <Widget>
            <MeasurementSingle measurement={measurement}/>
        </Widget>
    )
}

export default Single