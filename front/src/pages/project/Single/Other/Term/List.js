import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import TermList from '../../../components/List/Term'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const termList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getTermList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <TermList termList={termList}/>
        </Widget>
    )
}

export default List