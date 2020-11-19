import React from 'react'
import {Route, Switch} from 'react-router-dom'

import List from './List'
import Invite from './Invite'
import Edit from './Edit'

const Share = () => {
    return (
        <>
            <Route path="/me/project/:id/setting/member" component={List}/>
            <Switch>
                <Route path="/me/project/:id/setting/member/invite" component={Invite}/>
                <Route path="/me/project/:id/setting/member/:memberId" component={Edit}/>
            </Switch>
        </>
    )
}

export default Share