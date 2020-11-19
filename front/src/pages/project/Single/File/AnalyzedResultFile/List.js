import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import AnalyzedResultFileList from '../../../components/List/AnalyzedResultFile'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const analyzedResultFileList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getAnalyzedResultFileList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <AnalyzedResultFileList analyzedResultFileList={analyzedResultFileList}/>
        </Widget>
    )
}

export default List