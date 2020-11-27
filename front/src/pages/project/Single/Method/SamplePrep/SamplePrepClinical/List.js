import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import SamplePrepClinicalList from '../../../../components/List/SamplePrepClinical'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const samplePrepClinicalList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getSamplePrepClinicalList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <SamplePrepClinicalList samplePrepClinicalList={samplePrepClinicalList}/>
        </Widget>
    )
}

export default List