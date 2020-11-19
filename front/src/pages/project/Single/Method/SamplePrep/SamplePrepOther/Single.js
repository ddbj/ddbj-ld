import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepOtherSingle from '../../../../components/Single/SamplePrepOther'

const Single = ({match}) => {
    const {id, samplePrepOtherId} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepOther(projectBook, samplePrepOtherId)
    }, [samplePrepOtherId, projectBook])

    return (
        <Widget>
            <SamplePrepOtherSingle samplePrepOther={samplePrepOther}/>
        </Widget>
    )
}

export default Single