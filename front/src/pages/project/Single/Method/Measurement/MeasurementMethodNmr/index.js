import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useBuildPath, useBuildRoutePath} from '../../../../../../hooks/project/path'

import List from './List'
import Single from './Single'

const MeasurementMethodNmr = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    return (
        <Switch>
            <Route path={buildRoutePath('/method/measurement/measurement_method_nmr')} exact component={List}/>
            <Route path={buildRoutePath('/method/measurement/measurement_method_nmr/:measurementMethodNmrId')}
                   component={Single}/>
            <Redirect to={buildPath(id, '/method/measurement')}/>
        </Switch>
    )
}

export default MeasurementMethodNmr