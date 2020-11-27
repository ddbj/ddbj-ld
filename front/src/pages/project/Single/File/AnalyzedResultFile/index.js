import React from 'react'

import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const AnalyzedResultFile = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath("/file/analyzed_result_file")} exact component={List}/>
            <Route path={buildRoutePath("/file/analyzed_result_file/:analyzedResultFileId")} component={Single}/>
            <Redirect to={buildPath(id, "/file/analyzed_result_file")}/>
        </Switch>
    )
}

export default AnalyzedResultFile