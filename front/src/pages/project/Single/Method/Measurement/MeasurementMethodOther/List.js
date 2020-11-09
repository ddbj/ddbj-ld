import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import MeasurementMethodOtherList from '../../../../components/List/MeasurementMethodOther'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const measurementMethodOtherList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodOtherList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <MeasurementMethodOtherList measurementMethodOtherList={measurementMethodOtherList}/>
        </Widget>
    )
}

export default List