import React, {useMemo} from 'react'

import * as customizedViewService from '../../../../../services/projectBook/view/customized'

import {useProject, useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
// import ReferenceList from '../../../components/List/customized/Reference'
import ReferenceList from '../../../components/List/Customized/Reference'
import {useIsDraft} from "../../../../../hooks/project/status";

const List = ({match}) => {
    const {id} = match.params
    const projectBook = useProjectBook(id)

    const referenceList = useMemo(() => {
        if (!projectBook) return null
        return customizedViewService.getReferenceList(projectBook)
    }, [projectBook])

    if (!referenceList) {
        return null
    }

    return (
        <Widget>
            <ReferenceList referenceList={referenceList}/>
        </Widget>
    )
}

export default List