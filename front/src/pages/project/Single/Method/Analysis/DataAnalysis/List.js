import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import DataAnalysisList from '../../../../components/List/DataAnalysis'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const dataAnalysisList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getDataAnalysisList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <DataAnalysisList dataAnalysisList={dataAnalysisList}/>
        </Widget>
    )
}

export default List