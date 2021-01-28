import React from 'react'
import Header from '../header'
import {Redirect, Route, Switch} from "react-router-dom"
import Files from './files'
import Comments from "./comments"
import Requests from "./requests"
import {useEditingInfo} from "../../../../hooks/entries/jvar"
import Summary from "./summary";

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
                <Route path={"/entries/jvar/:entryUUID/summary"} component={Summary}/>
                <Redirect path="*" to={`/entries/jvar/${entryUUID}/files`}/>
            </Switch>
        </>
    )
}

export default Edit