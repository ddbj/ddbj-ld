import React, {useMemo} from 'react'

import * as customizedViewService from '../../../../../services/projectBook/view/customized'

import {useProjectBook} from '../../../../../hooks/project/projectBook'

import Widget from '../../../../../components/Widget'
import ExperimentalDesignList from '../../../components/List/Customized/ExperimentalDesign'

const List = ({match}) => {
    const {id} = match.params
    const projectBook = useProjectBook(id)

    const experimentalDesignList = useMemo(() => {
        if (!projectBook) return null
        return customizedViewService.getExperimentalDesignList(projectBook)
    }, [projectBook])

    return (
        <Widget>
            <ExperimentalDesignList experimentalDesignList={experimentalDesignList}/>
        </Widget>
    )
}

export default List