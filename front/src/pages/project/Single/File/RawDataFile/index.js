import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const RawDataFile = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath("/file/raw_data_file")} exact component={List}/>
            <Route path={buildRoutePath("/file/raw_data_file/:rawDataFileId")} component={Single}/>
            <Redirect to={buildPath(id, "/file/raw_data_file")}/>
        </Switch>
    )
}

export default RawDataFile