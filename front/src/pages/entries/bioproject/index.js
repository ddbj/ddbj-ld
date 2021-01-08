import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import List from './List'
import Edit from './edit'

const  Entries = () => (
    <Switch>
        <Route path="/entries/bioproject/:uuid" component={Edit}/>
        <Route path="/entries/bioproject" component={List}/>
        <Redirect to="/search"/>
    </Switch>
)

export default Entries