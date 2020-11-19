import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import ExtractionMethodSingle from '../../../../components/Single/ExtractionMethod'

const Single = ({match}) => {
    const {id, extractionMethodId} = match.params

    const projectBook = useProjectBook(id)
    const extractionMethod = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExtractionMethod(projectBook, extractionMethodId)
    }, [extractionMethodId, projectBook])

    return (
        <Widget>
            <ExtractionMethodSingle extractionMethod={extractionMethod}/>
        </Widget>
    )
}

export default Single