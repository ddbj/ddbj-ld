import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepClinicalSingle from '../../../../components/Single/SamplePrepClinical'

const Single = ({match}) => {
    const {id, samplePrepClinicalId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepClinical = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepClinical(projectBook, samplePrepClinicalId)
    }, [samplePrepClinicalId, projectBook])

    return (
        <Widget>
            <SamplePrepClinicalSingle samplePrepClinical={samplePrepClinical}/>
        </Widget>
    )
}

export default Single