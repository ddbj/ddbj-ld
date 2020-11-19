import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const AnotationMethod = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/method/analysis/annotation_method')} exact component={List}/>
            <Route path={buildRoutePath('/method/analysis/annotation_method/:annotationMethodId')} component={Single}/>
            <Redirect to={buildPath(id, '/method/analysis')}/>
        </Switch>
    )
}

export default AnotationMethod