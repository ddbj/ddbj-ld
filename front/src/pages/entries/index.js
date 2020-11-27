import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import JVar from './jvar'
import BioProject from './bioproject'
import BioSample from './biosample'
import Trad from './trad'


const  Entries = () => (
    <Switch>
        <Route path="/entries/jvar" component={JVar}/>
        <Route path="/entries/bioproject" component={BioProject}/>
        <Route path="/entries/biosample" component={BioSample}/>
        <Route path="/entries/trad" component={Trad}/>
        <Redirect to="/entries/jvar"/>
    </Switch>
)

export default Entries