import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import DataPreprocessingList from '../../../../components/List/DataPreprocessing'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const dataPreprocessingList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataPreprocessingList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <DataPreprocessingList dataPreprocessingList={dataPreprocessingList}/>
        </Widget>
    )
}

export default List