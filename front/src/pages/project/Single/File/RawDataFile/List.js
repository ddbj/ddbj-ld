import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import RawDataFileList from '../../../components/List/RawDataFile'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const rawDataFileList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getRawDataFileList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <RawDataFileList rawDataFileList={rawDataFileList}/>
        </Widget>
    )
}

export default List