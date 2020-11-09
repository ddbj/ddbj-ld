import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const SampleChemical = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/sample/sample_chemical')} exact component={List}/>
            <Route path={buildRoutePath('/sample/sample_chemical/:sampleChemicalId')} component={Single}/>
            <Redirect to={buildPath(id, '/sample/sample_chemical')}/>
        </Switch>
    )
}

export default SampleChemical