import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import SoftwareList from '../../../components/List/Software'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const softwareList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSoftwareList(projectBook)
    }, [projectBook])

    // FIXME 表示を改善
    if (!projectBook) {
        return null
    }

    return (
        <Widget>
            <SoftwareList softwareList={softwareList}/>
        </Widget>
    )
}

export default List