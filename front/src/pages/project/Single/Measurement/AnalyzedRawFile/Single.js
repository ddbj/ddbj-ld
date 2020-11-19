import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import AnalyzedRawFileSingle from '../../../components/Single/AnalyzedRawFile'

const Single = ({match}) => {
    const {id, analyzedRawFileId} = match.params

    const projectBook = useProjectBook(id)
    const analyzedRawFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnalyzedRawFile(projectBook, analyzedRawFileId)
    }, [projectBook, analyzedRawFileId])

    return (
        <Widget>
            <AnalyzedRawFileSingle analyzedRawFile={analyzedRawFile}/>
        </Widget>
    )
}

export default Single