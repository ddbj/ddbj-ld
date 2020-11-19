import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import OrganisationList from '../../../components/List/Organisation'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const organisationList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getOrganisationList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <OrganisationList organisationList={organisationList}/>
        </Widget>
    )
}

export default List