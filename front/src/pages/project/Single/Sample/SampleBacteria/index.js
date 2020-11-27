import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const SampleBacteria = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/sample/sample_bacteria')} exact component={List}/>
            <Route path={buildRoutePath('/sample/sample_bacteria/:sampleBacteriaId')} component={Single}/>
            <Redirect to={buildPath(id, '/sample/sample_bacteria')}/>
        </Switch>
    )
}

export default SampleBacteria