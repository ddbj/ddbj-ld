import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import ExtractionMethodList from '../../../../components/List/ExtractionMethod'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const extractionMethodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getExtractionMethodList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <ExtractionMethodList extractionMethodList={extractionMethodList}/>
        </Widget>
    )
}

export default List