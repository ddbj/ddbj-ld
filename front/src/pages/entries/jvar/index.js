import React from 'react'
<<<<<<< HEAD
import { Route, Switch } from 'react-router-dom'
import List from './List'
import Edit from './edit'
import Create from "./Create"
import Delete from "./Delete"
import Validate from "./Validate"
import Submit from "./Submit"

const  Entries = () => (
    <Switch>
        <Route path="/entries/jvar/create" component={Create} />
        <Route path="/entries/jvar/:entryUUID/delete" component={Delete} />
        <Route path="/entries/jvar/:entryUUID/validate" component={Validate} />
        <Route path="/entries/jvar/:entryUUID/submit" component={Submit} />
        <Route path="/entries/jvar/:entryUUID" component={Edit} />
        <Route path="/entries/jvar" component={List} />
    </Switch>
)

export default Entries
=======
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
>>>>>>> 差分修正
