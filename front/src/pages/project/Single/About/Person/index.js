import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const Person = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath("/about/person")} exact component={List}/>
            <Route path={buildRoutePath("/about/person/:personId")} component={Single}/>
            <Redirect to={buildPath(id, "/about/person")}/>
        </Switch>
    )
}

export default Person