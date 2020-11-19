import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import ReferenceSingle from '../../../components/Single/Reference'

const Single = ({match}) => {
    const {id, referenceId} = match.params
    const projectBook = useProjectBook(id)

    const reference = useMemo(() => {
        if (!projectBook) return null
        return viewService.getReference(projectBook, referenceId)
    }, [projectBook, referenceId])

    return (
        <Widget>
            <ReferenceSingle reference={reference}/>
        </Widget>
    )
}

export default Single