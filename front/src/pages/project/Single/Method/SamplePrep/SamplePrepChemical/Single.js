import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepChemicalSingle from '../../../../components/Single/SamplePrepChemical'

const Single = ({match}) => {
    const {id, samplePrepChemicalId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepChemical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepChemical(projectBook, samplePrepChemicalId)
    }, [samplePrepChemicalId, projectBook])

    return (
        <Widget>
            <SamplePrepChemicalSingle samplePrepChemical={samplePrepChemical}/>
        </Widget>
    )
}

export default Single