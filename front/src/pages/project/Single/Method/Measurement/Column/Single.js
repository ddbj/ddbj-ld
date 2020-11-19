import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import ColumnSingle from '../../../../components/Single/Column'

const Single = ({match}) => {
    const {id, columnId} = match.params

    const projectBook = useProjectBook(id)
    const column = useMemo(() => {
        if (!projectBook) return null
        return viewService.getColumn(projectBook, columnId)
    }, [columnId, projectBook])

    return (
        <Widget>
            <ColumnSingle column={column}/>
        </Widget>
    )
}

export default Single