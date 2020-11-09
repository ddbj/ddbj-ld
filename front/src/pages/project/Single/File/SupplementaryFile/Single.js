import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SupplementaryFileSingle from '../../../components/Single/SupplementaryFile'

const Single = ({match}) => {
    const {id, supplementaryFileId} = match.params

    const projectBook = useProjectBook(id)
    const supplementaryFile = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSupplementaryFile(projectBook, supplementaryFileId)
    }, [projectBook, supplementaryFileId])

    return (
        <Widget>
            <SupplementaryFileSingle supplementaryFile={supplementaryFile}/>
        </Widget>
    )
}

export default Single