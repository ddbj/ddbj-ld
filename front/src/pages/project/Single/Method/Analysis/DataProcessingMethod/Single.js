import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import DataProcessingMethodSingle from '../../../../components/Single/DataProcessingMethod'

const Single = ({match}) => {
    const {id, dataProcessingMethodId} = match.params

    const projectBook = useProjectBook(id)
    const dataProcessingMethod = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethod(projectBook, dataProcessingMethodId)
    }, [dataProcessingMethodId, projectBook])

    return (
        <Widget>
            <DataProcessingMethodSingle dataProcessingMethod={dataProcessingMethod}/>
        </Widget>
    )
}

export default Single