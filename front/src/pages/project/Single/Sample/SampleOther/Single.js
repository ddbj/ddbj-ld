import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleOtherSingle from '../../../components/Single/SampleOther'

const Single = ({match}) => {
    const {id, sampleOtherId} = match.params

    const projectBook = useProjectBook(id)
    const sampleOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSampleOther(projectBook, sampleOtherId)
    }, [projectBook, sampleOtherId])

    return (
        <Widget>
            <SampleOtherSingle sampleOther={sampleOther}/>
        </Widget>
    )
}

export default Single