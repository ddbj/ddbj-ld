import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepControlSingle from '../../../../components/Single/SamplePrepControl'

const Single = ({match}) => {
    const {id, samplePrepControlId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepControl = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepControl(projectBook, samplePrepControlId)
    }, [samplePrepControlId, projectBook])

    return (
        <Widget>
            <SamplePrepControlSingle samplePrepControl={samplePrepControl}/>
        </Widget>
    )
}

export default Single