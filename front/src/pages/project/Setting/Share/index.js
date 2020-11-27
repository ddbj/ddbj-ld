import React from 'react'
import {Route, Switch} from 'react-router-dom'

import List from './List'
import Create from './Create'
import Edit from './Edit'
import Delete from "./Delete";

const Share = () => (
    <>
        <Route path="/me/project/:id/setting/share" component={List}/>
        <Switch>
            <Route path="/me/project/:id/setting/share/create" component={Create}/>
            <Route path="/me/project/:id/setting/share/:token/edit" component={Edit}/>
            <Route path="/me/project/:id/setting/share/:token/delete" component={Delete}/>
        </Switch>
    </>
)

export default Share