import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const DataAnalysis = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/method/analysis/data_analysis')} exact component={List}/>
            <Route path={buildRoutePath('/method/analysis/data_analysis/:dataAnalysisId')} component={Single}/>
            <Redirect to={buildPath(id, '/method/analysis')}/>
        </Switch>
    )
}

export default DataAnalysis