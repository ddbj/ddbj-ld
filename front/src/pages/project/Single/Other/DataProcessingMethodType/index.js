import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const DataProcessingMethodType = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/other/data_processing_method_type')} exact component={List}/>
            <Route path={buildRoutePath('/other/data_processing_method_type/:dataProcessingMethodTypeId')}
                   component={Single}/>
            <Redirect to={buildPath(id, '/other')}/>
        </Switch>
    )
}

export default DataProcessingMethodType