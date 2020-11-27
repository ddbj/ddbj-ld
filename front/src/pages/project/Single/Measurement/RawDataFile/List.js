import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import RawDataFileList from '../../../components/List/RawDataFile'

const List = ({match}) => {
    const {id} = match.params

    const projectBoook = useProjectBook(id)
    const rawDataFileList = useMemo(() => {
        if (!projectBoook) return null
        return viewService.getRawDataFileList(projectBoook)
    }, [projectBoook])

    return (
        <Widget>
            <RawDataFileList rawDataFileList={rawDataFileList}/>
        </Widget>
    )
}

export default List