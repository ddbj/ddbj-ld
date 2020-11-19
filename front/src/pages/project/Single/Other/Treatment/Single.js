import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import TreatmentSingle from '../../../components/Single/Treatment'

const Single = ({match}) => {
    const {id, treatmentId} = match.params

    const projectBook = useProjectBook(id)
    const treatment = useMemo(() => {
        if (!projectBook) return null
        return viewService.getTreatment(projectBook, treatmentId)
    }, [projectBook, treatmentId])

    return (
        <Widget>
            <TreatmentSingle treatment={treatment}/>
        </Widget>
    )
}

export default Single