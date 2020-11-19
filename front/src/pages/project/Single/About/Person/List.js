import React, {useMemo} from 'react'

import * as customizedViewService from '../../../../../services/projectBook/view/customized'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import PersonList from '../../../components/List/Customized/Person'

const List = ({match}) => {
    const {id} = match.params
    const projectBook = useProjectBook(id)

    const personList = useMemo(() => {
        if (!projectBook) return null
        return customizedViewService.getPersonList(projectBook)
    }, [projectBook])

    if (!personList) {
        return null
    }

    return (
        <Widget>
            <PersonList personList={personList}/>
        </Widget>
    )
}

export default List