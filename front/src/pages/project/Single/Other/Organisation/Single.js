import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import OrganisationSingle from '../../../components/Single/Organisation'

const Single = ({match}) => {
    const {id, organisationId} = match.params

    const projectBook = useProjectBook(id)
    const organisation = useMemo(() => {
        if (!projectBook) return null
        return viewService.getOrganisation(projectBook, organisationId)
    }, [projectBook, organisationId])

    return (
        <Widget>
            <OrganisationSingle organisation={organisation}/>
        </Widget>
    )
}

export default Single