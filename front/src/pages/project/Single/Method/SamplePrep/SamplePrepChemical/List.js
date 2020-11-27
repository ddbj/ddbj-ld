import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepChemicalList from '../../../../components/List/SamplePrepChemical'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepChemicalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepChemicalList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepChemicalList samplePrepChemicalList={samplePrepChemicalList}/>
        </Widget>
    )
}

export default List