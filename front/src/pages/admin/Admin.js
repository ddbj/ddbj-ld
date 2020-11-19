import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import Project from './Project'
import Statistic from './Statistic'

const Admin = () => (
    <Switch>
        <Route path="/admin/project" component={Project}/>
        <Route path="/admin/statistic" component={Statistic}/>
        <Redirect to="/admin/project"/>
    </Switch>
)

export default Admin