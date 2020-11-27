import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleChemicalList from '../../../components/List/SampleChemical'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleChemicalList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleChemicalList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleChemicalList sampleChemicalList={sampleChemicalList}/>
        </Widget>
    )
}

export default List