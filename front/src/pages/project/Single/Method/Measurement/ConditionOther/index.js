import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const Column = ({match}) => {
    const {id, measurementMethodType, measurementMethodId} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route
                path={buildRoutePath('/method/measurement/:measurementMethodType/:measurementMethodId/condition_other')}
                exact component={List}/>
            <Route
                path={buildRoutePath('/method/measurement/:measurementMethodType/:measurementMethodId/condition_other/:conditionOtherId')}
                component={Single}/>
            <Redirect to={buildPath(id, `/method/measurement/${measurementMethodType}/${measurementMethodId}`)}/>
        </Switch>
    )
}

export default Column
