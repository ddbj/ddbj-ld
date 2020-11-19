import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import Single from './Single'
import Setting from './Setting'

const Project = () => {
    return (
        <Switch>
            <Route path="/me/project/:id/setting" component={Setting}/>
            <Route path="/me/project/:id/draft" component={Single}/>
            <Route path="/me/project/:id" component={Single}/>
            <Route path="/project/:id/draft" component={Single}/>
            <Route path="/project/:id" component={Single}/>
            <Redirect to="/search"/>
        </Switch>
    )
}


export default Project