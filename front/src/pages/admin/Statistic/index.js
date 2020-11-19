import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import Projects from './Projects'
import Users from './Users'

const Statistic = () => (
    <Switch>
        <Route path="/admin/statistic/project" component={Projects}/>
        <Route path="/admin/statistic/users" component={Users}/>
        <Redirect to="/admin/statistic/projcts"/>
    </Switch>
)

export default Statistic