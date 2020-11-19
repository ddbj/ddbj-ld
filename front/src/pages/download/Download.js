import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import Editor from './Editor'

const Download = () => (
    <Switch>
        {/*<Route path="/download/editor" component={Editor}/>*/}
        {/*<Redirect to="/download"/>*/}
    </Switch>
)

export default Download