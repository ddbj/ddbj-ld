import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import DataProcessingMethodTypeSingle from '../../../components/Single/DataProcessingMethodType'

const Single = ({match}) => {
    const {id, dataProcessingMethodTypeId} = match.params

    const projectBook = useProjectBook(id)
    const dataProcessingMethodType = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethodType(projectBook, dataProcessingMethodTypeId)
    }, [dataProcessingMethodTypeId, projectBook])

    return (
        <Widget>
            <DataProcessingMethodTypeSingle dataProcessingMethodType={dataProcessingMethodType}/>
        </Widget>
    )
}

export default Single