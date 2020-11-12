import React from 'react'
import { Header } from '../components/Header'
import {Redirect, Route, Switch} from "react-router-dom"
import Files from './files'
import Summary from "./Summary"
import Comment from "./Comment"

const Edit = ({match, history}) => {
    const { uuid } = match.params

    // FIXME、uuidをベースにentryに移動する

    return (
        <>
            <Route path="/entries/jvar/:uuid" component={Header}/>
            <Switch>
                <Route path={"/entries/jvar/:uuid/files"} component={Files}/>
                <Route path={"/entries/jvar/:uuid/summary"} component={Summary}/>
                <Route path={"/entries/jvar/:uuid/comment"} component={Comment}/>
                <Redirect path="*" to={`/entries/jvar/${uuid}/files`}/>
            </Switch>
        </>
    )
}

export default Edit