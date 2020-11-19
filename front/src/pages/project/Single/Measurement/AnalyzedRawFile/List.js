import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import AnalyzedRawFileList from '../../../components/List/AnalyzedRawFile'

const List = ({match}) => {
    const {id} = match.params

    const projectBoook = useProjectBook(id)
    const analyzedRawFileList = useMemo(() => {
        if (!projectBoook) return null
        return viewService.getAnalyzedRawFileList(projectBoook)
    }, [projectBoook])

    return (
        <Widget>
            <AnalyzedRawFileList analyzedRawFileList={analyzedRawFileList}/>
        </Widget>
    )
}

export default List