import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import InstrumentList from '../../../../components/List/Instrument'

const List = ({match}) => {
    const {id} = match.params

    const projectBook = useProjectBook(id)
    const instrumentList = useMemo(() => {
        if (!projectBook) return null
        return viewService.getInstrumentList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <InstrumentList instrumentList={instrumentList}/>
        </Widget>
    )
}

export default List