import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import {Nav} from 'reactstrap'

import {NavLink} from '../../../components/Navigation'

import Profile from './Profile'

const Setting = () => {
    return (
        <>
            <Nav pills className="mb-4">
                <NavLink to={`/me/setting/profile`}>プロフィール</NavLink>
            </Nav>
            <Switch>
                <Route path="/me/setting/profile" component={Profile}/>
                <Redirect to="/me/setting/profile"/>
            </Switch>
        </>
    )
}

export default Setting