import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import DataAnalysisSingle from '../../../../components/Single/DataAnalysis'

const Single = ({match}) => {
    const {id, dataAnalysisId} = match.params

    const projectBook = useProjectBook(id)
    const dataAnalysis = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataAnalysis(projectBook, dataAnalysisId)
    }, [dataAnalysisId, projectBook])

    return (
        <Widget>
            <DataAnalysisSingle dataAnalysis={dataAnalysis}/>
        </Widget>
    )
}

export default Single