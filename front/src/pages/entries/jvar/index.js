import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import List from './List'
import Edit from './edit'
import Create from "./Create"
import Delete from "./Delete";

const  Entries = () => (
    <Switch>
        <Route path="/entries/jvar/create" component={Create}/>
        <Route path="/entries/jvar/:uuid/delete" component={Delete}/>
        <Route path="/entries/jvar/:uuid" component={Edit}/>
        <Route path="/entries/jvar" component={List}/>
        <Redirect to="/search"/>
    </Switch>
)

export default Entries