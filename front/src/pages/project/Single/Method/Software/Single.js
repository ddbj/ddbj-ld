import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SoftwareSingle from '../../../components/Single/Software'

const Single = ({match}) => {
    const {id, softwareId} = match.params

    const projectBook = useProjectBook(id)
    const software = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSoftware(projectBook, softwareId)
    }, [projectBook, softwareId])

    return (
        <Widget>
            <SoftwareSingle software={software}/>
        </Widget>
    )
}

export default Single