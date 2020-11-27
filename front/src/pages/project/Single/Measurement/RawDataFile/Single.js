import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import RawDataFileSingle from '../../../components/Single/RawDataFile'

const Single = ({match}) => {
    const {id, rawDataFileId} = match.params

    const projectBook = useProjectBook(id)
    const rawDataFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getRawDataFile(projectBook, rawDataFileId)
    }, [projectBook, rawDataFileId])

    return (
        <Widget>
            <RawDataFileSingle rawDataFile={rawDataFile}/>
        </Widget>
    )
}

export default Single