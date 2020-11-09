import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SupplementaryFileList from '../../../components/List/SupplementaryFile'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const supplementaryFileList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSupplementaryFileList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SupplementaryFileList supplementaryFileList={supplementaryFileList}/>
        </Widget>
    )
}

export default List