import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const Treatment = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath("/other/term")} exact component={List}/>
            <Route path={buildRoutePath("/other/term/:termId")} component={Single}/>
            <Redirect to={buildPath(id, "/other/term")}/>
        </Switch>
    )
}

export default Treatment