import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import PersonSingle from '../../../components/Single/Person'

const Single = ({match}) => {
    const {id, personId} = match.params
    const projectBook = useProjectBook(id)

    const person = useMemo(() => {
        if (!projectBook) return null
        return viewService.getPerson(projectBook, personId)
    }, [personId, projectBook])

    return (
        <Widget>
            <PersonSingle person={person}/>
        </Widget>
    )
}

export default Single