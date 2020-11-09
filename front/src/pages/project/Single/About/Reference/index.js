import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const Reference = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath("/about/reference")} exact component={List}/>
            <Route path={buildRoutePath("/about/reference/:referenceId")} component={Single}/>
            <Redirect to={buildPath(id, '/about/reference')}/>
        </Switch>
    )
}

export default Reference