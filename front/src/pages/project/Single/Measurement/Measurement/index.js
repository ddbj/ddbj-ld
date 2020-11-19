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
                <Route path={buildRoutePath('/measurement/measurement')} exact component={List}/>
                <Route path={buildRoutePath('/measurement/measurement/:measurementId')} component={Single}/>
                <Redirect to={buildPath(id, '/measurement/measurement')}/>
            </Switch>
        </>
    )
}

export default Experiment