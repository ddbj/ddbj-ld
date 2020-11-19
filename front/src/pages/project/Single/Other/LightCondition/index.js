import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const LightCondition = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath("/other/light_condition")} exact component={List}/>
            <Route path={buildRoutePath("/other/light_condition/:lightConditionId")} component={Single}/>
            <Redirect to={buildPath(id, "/other/light_condition")}/>
        </Switch>
    )
}

export default LightCondition