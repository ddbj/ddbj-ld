import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import SampleClinicalList from '../../../components/List/SampleClinical'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const sampleClinicalList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getSampleClinicalList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SampleClinicalList sampleClinicalList={sampleClinicalList}/>
        </Widget>
    )
}

export default List