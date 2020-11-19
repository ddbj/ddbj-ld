import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const Software = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/method/software')} exact component={List}/>
            <Route path={buildRoutePath('/method/software/:softwareId')} component={Single}/>
            <Redirect to={buildPath(id, '/method/software')}/>
        </Switch>
    )
}

export default Software