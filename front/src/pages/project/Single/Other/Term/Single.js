import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import TermSingle from '../../../components/Single/Term'

const Single = ({match}) => {
    const {id, termId} = match.params

    const projectBook = useProjectBook(id)
    const term = useMemo(() => {
        if (!projectBook) return null
        return viewService.getTerm(projectBook, termId)
    }, [projectBook, termId])

    return (
        <Widget>
            <TermSingle term={term}/>
        </Widget>
    )
}

export default Single