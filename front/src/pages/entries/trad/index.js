import React from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import List from './List'
import Edit from './edit'

const  Entries = () => (
<<<<<<< HEAD
    <>
        Under Construction...
    </>
    // <Switch>
    //     <Route path="/entries/trad/:uuid" component={Edit}/>
    //     <Route path="/entries/trad" component={List}/>
    //     <Redirect to="/search"/>
    // </Switch>
)

export default Entries
=======
    <Switch>
        <Route path="/entries/trad/:uuid" component={Edit}/>
        <Route path="/entries/trad" component={List}/>
        <Redirect to="/search"/>
    </Switch>
)

export default Entries
>>>>>>> 差分修正
