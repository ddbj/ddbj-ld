import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleControlSingle from '../../../components/Single/SampleControl'

const Single = ({match}) => {
    const {id, sampleControlId} = match.params

    const projectBook = useProjectBook(id)
    const sampleControl = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleControl(projectBook, sampleControlId)
    }, [projectBook, sampleControlId])

    return (
        <Widget>
            <SampleControlSingle sampleControl={sampleControl}/>
        </Widget>
    )
}

export default Single