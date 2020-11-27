import React from 'react'
<<<<<<< HEAD
import Header from '../header'
import {
    Redirect,
    Route,
    Switch
} from "react-router-dom"

import Files from './files'
import Comments from "./comments"
import Requests from "./requests"
import { useEditingInfo } from "../../../../hooks/entries/jvar"
import Summary from "./summary"
import Submitters from "./submitters"
import Status from "./status"

const Edit = ({match, history}) => {
    const { entryUUID } = match.params

    const {
        loading
    } = useEditingInfo(history, entryUUID)

    if(loading) {
        return <>Loading...</>
    }

    return (
        <>
            <Route path="/entries/jvar/:entryUUID" component={Header}/>
            <Switch>
                <Route path={"/entries/jvar/:entryUUID/files"} component={Files}/>
                <Route path={"/entries/jvar/:entryUUID/comments"} component={Comments}/>
                <Route path={"/entries/jvar/:entryUUID/requests"} component={Requests}/>
                <Route path={"/entries/jvar/:entryUUID/submitters"} component={Submitters}/>
                <Route path={"/entries/jvar/:entryUUID/summary"} component={Summary}/>
                <Route path={"/entries/jvar/:entryUUID/status"} component={Status}/>
                <Redirect path="*" to={`/entries/jvar/${entryUUID}/files`}/>
=======
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
>>>>>>> 差分修正
            </Switch>
        </>
    )
}

<<<<<<< HEAD
export default Edit
=======
export default Edit
>>>>>>> 差分修正
