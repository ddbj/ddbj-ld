import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import ColumnList from '../../../../components/List/Column'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const columnList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getColumnList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <ColumnList columnList={columnList}/>
        </Widget>
    )
}

export default List