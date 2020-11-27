import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import AnnotationMethodList from '../../../../components/List/AnnotationMethod'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const annotationMethodList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnnotationMethodList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <AnnotationMethodList annotationMethodList={annotationMethodList}/>
        </Widget>
    )
}

export default List