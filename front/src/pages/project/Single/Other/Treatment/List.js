import React, {useMemo} from 'react'

import * as viewService from '../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget/Widget'
import TreatmentList from '../../../components/List/Treatment'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const treatmentList = useMemo(() => {
        if (!projectBook) return []
        return viewService.getTreatmentList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <TreatmentList treatmentList={treatmentList}/>
        </Widget>
    )
}

export default List