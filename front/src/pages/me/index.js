import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import Project from './Project'
import Setting from './Setting'
import MyProjectSetting from '../project/Setting'
import Single from "../project/Single";
import MyPage from './Me'

const Me = () => (
    <Switch>
        {/*<Route path="/me/project/:id/setting" component={MyProjectSetting}/>*/}
        {/*<Route path="/me/project/:id/draft" component={Single}/>*/}
        {/*<Route path="/me/project/:id" component={Single}/>*/}
        {/*<Route path="/me/project" component={Project}/>*/}
        {/*<Route path="/me/setting" component={Setting}/>*/}
        {/* FIXME ここのcomponent名を考える */}
        <Route path="/me" component={MyPage}/>
        <Redirect to="/me"/>
    </Switch>
)

export default Me