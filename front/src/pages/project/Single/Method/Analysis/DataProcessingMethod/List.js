import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import DataProcessingMethodList from '../../../../components/List/DataProcessingMethod'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const dataProcessingMethodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethodList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <DataProcessingMethodList dataProcessingMethodList={dataProcessingMethodList}/>
        </Widget>
    )
}

export default List