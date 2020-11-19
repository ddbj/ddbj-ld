import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const MeasurementMethodLcms = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/method/measurement/measurement_method_lcms')} exact component={List}/>
            <Route path={buildRoutePath('/method/measurement/measurement_method_lcms/:measurementMethodLcmsId')}
                   component={Single}/>
            <Redirect to={buildPath(id, '/method/measurement')}/>
        </Switch>
    )
}

export default MeasurementMethodLcms