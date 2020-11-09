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
            <Route path="/entries/bioproject/:uuid" component={Header}/>
            <Switch>
                <Route path={"/entries/bioproject/:uuid/files"} component={Files}/>
                <Route path={"/entries/bioproject/:uuid/summary"} component={Summary}/>
                <Route path={"/entries/bioproject/:uuid/comment"} component={Comment}/>
                <Redirect path="*" to={`/entries/bioproject/${uuid}/files`}/>
            </Switch>
        </>
    )
}

export default Edit