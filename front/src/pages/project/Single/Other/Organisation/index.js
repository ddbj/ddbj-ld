import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const Organisation = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath("/other/organisation")} exact component={List}/>
            <Route path={buildRoutePath("/other/organisation/:organisationId")} component={Single}/>
            <Redirect to={buildPath(id, "/other/organisation")}/>
        </Switch>
    )
}

export default Organisation