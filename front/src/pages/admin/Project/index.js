import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom'

import Applied from './Applied'

const Project = () => {
    return (
        <>
            <Switch>
                <Route path="/admin/project/published" component={Applied}/>
                <Redirect to="/admin/project/published"/>
            </Switch>
        </>
    )
}

export default Project