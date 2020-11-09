import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import AnnotationMethodSingle from '../../../../components/Single/AnnotationMethod'

const Single = ({match}) => {
    const {id, annotationMethodId} = match.params

    const projectBook = useProjectBook(id)
    const annotationMethod = useMemo(() => {
        if (!projectBook) return null
        return viewService.getAnnotationMethod(projectBook, annotationMethodId)
    }, [annotationMethodId, projectBook])

    return (
        <Widget>
            <AnnotationMethodSingle annotationMethod={annotationMethod}/>
        </Widget>
    )
}

export default Single