import React from 'react'
import { Header } from '../header'
import {Redirect, Route, Switch} from "react-router-dom"
import Files from './files'
import Comments from "./comments"
import {useEditingInfo} from "../../../../hooks/entries/jvar";

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
                <Redirect path="*" to={`/entries/jvar/${entryUUID}/files`}/>
            </Switch>
        </>
    )
}

export default Edit