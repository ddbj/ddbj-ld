import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import DataProcessingMethodTypeList from '../../../components/List/DataProcessingMethodType'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const dataProcessingMethodTypeList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataProcessingMethodTypeList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <DataProcessingMethodTypeList dataProcessingMethodTypeList={dataProcessingMethodTypeList}/>
        </Widget>
    )
}

export default List