import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleChemicalSingle from '../../../components/Single/SampleChemical'

const Single = ({match}) => {
    const {id, sampleChemicalId} = match.params

    const projectBook = useProjectBook(id)
    const sampleChemical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleChemical(projectBook, sampleChemicalId)
    }, [projectBook, sampleChemicalId])

    return (
        <Widget>
            <SampleChemicalSingle sampleChemical={sampleChemical}/>
        </Widget>
    )
}

export default Single