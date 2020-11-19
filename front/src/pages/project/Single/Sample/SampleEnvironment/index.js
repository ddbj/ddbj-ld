import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const SampleEnvironment = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/sample/sample_environment')} exact component={List}/>
            <Route path={buildRoutePath('/sample/sample_environment/:sampleEnvironmentId')} component={Single}/>
            <Redirect to={buildPath(id, '/sample/sample_environment')}/>
        </Switch>
    )
}

export default SampleEnvironment