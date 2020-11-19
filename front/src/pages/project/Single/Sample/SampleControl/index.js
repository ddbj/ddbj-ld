import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const SampleControl = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/sample/sample_control')} exact component={List}/>
            <Route path={buildRoutePath('/sample/sample_control/:sampleControlId')} component={Single}/>
            <Redirect to={buildPath(id, '/sample/sample_control')}/>
        </Switch>
    )
}

export default SampleControl