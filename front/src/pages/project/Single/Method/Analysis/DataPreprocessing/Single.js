import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import DataPreprocessingSingle from '../../../../components/Single/DataPreprocessing'

const Single = ({match}) => {
    const {id, dataPreprocessingId} = match.params

    const projectBook = useProjectBook(id)
    const dataPreprocessing = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataPreprocessing(projectBook, dataPreprocessingId)
    }, [dataPreprocessingId, projectBook])

    return (
        <Widget>
            <DataPreprocessingSingle dataPreprocessing={dataPreprocessing}/>
        </Widget>
    )
}

export default Single