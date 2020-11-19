import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const SamplePrepEnvironment = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/method/sample_prep/sample_prep_environment')} exact component={List}/>
            <Route path={buildRoutePath('/method/sample_prep/sample_prep_environment/:samplePrepEnvironmentId')}
                   component={Single}/>
            <Redirect to={buildPath(id, '/method/sample_prep')}/>
        </Switch>
    )
}

export default SamplePrepEnvironment