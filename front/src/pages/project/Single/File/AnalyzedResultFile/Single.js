import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import AnalyzedResultFileSingle from '../../../components/Single/AnalyzedResultFile'

const Single = ({match}) => {
    const {id, analyzedResultFileId} = match.params

    const projectBook = useProjectBook(id)
    const analyzedResultFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnalyzedResultFile(projectBook, analyzedResultFileId)
    }, [analyzedResultFileId, projectBook])

    return (
        <Widget>
            <AnalyzedResultFileSingle analyzedResultFile={analyzedResultFile} />
        </Widget>
    )
}

export default Single