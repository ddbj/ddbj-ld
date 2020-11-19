import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import Single from './Single'
import List from './List'

const Experiment = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <>
            <Switch>
                <Route path={buildRoutePath('/measurement/analyzed_raw_file')} exact component={List}/>
                <Route path={buildRoutePath('/measurement/analyzed_raw_file/:analyzedRawFileId')} component={Single}/>
                <Redirect to={buildPath(id, '/measurement/analyzed_raw_file')}/>
            </Switch>
        </>
    )
}

export default Experiment