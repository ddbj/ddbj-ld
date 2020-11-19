import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const SamplePlant = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/sample/sample_plant')} exact component={List}/>
            <Route path={buildRoutePath('/sample/sample_plant/:samplePlantId')} component={Single}/>
            <Redirect to={buildPath(id, '/sample/sample_plant')}/>
        </Switch>
    )
}

export default SamplePlant
