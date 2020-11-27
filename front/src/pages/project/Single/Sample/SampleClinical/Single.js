import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleClinicalSingle from '../../../components/Single/SampleClinical'

const Single = ({match}) => {
    const {id, sampleClinicalId} = match.params

    const projectBook = useProjectBook(id)
    const sampleClinical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleClinical(projectBook, sampleClinicalId)
    }, [projectBook, sampleClinicalId])

    return (
        <Widget>
            <SampleClinicalSingle sampleClinical={sampleClinical}/>
        </Widget>
    )
}

export default Single