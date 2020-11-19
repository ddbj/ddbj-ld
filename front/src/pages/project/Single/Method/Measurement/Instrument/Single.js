import React, {useMemo} from 'react'

import * as viewService from '../../../../../../services/projectBook/view'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'

import Widget from '../../../../../../components/Widget'
import InstrumentSingle from '../../../../components/Single/Instrument'

const Single = ({match}) => {
    const {id, instrumentId} = match.params

    const projectBook = useProjectBook(id)
    const instrument = useMemo(() => {
        if (!projectBook) return null
        return viewService.getInstrument(projectBook, instrumentId)
    }, [instrumentId, projectBook])

    return (
        <Widget>
            <InstrumentSingle instrument={instrument}/>
        </Widget>
    )
}

export default Single