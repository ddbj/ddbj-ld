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
            <Route path="/entries/biosample/:uuid" component={Header}/>
            <Switch>
                <Route path={"/entries/biosample/:uuid/files"} component={Files}/>
                <Route path={"/entries/biosample/:uuid/summary"} component={Summary}/>
                <Route path={"/entries/biosample/:uuid/comment"} component={Comment}/>
                <Redirect path="*" to={`/entries/biosample/${uuid}/files`}/>
            </Switch>
        </>
    )
}

export default Edit