import React from 'react'
import {Route} from 'react-router-dom'

import List from './List'
import Create from './Create'

const Project = () => (
    <>
        <Route path="/me/project" component={List}/>
        <Route path="/me/project/create" component={Create}/>
    </>
)

export default Project