import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleOtherList from '../../../components/List/SampleOther'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleOtherList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleOtherList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleOtherList sampleOtherList={sampleOtherList}/>
        </Widget>
    )
}

export default List