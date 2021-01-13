import React from 'react'
import { Route, Switch } from "react-router-dom"
import List from "./List"
import { useEditingInfo } from "../../../../../hooks/entries/jvar"
import Publish from "./Publish"
import Cancel from "./Cancel"
import Update from "./Update"

const Requests = ({match, history}) => {
    const { entryUUID } = match.params

    const {
        loading,
        currentEntry,
    } = useEditingInfo(history, entryUUID)

    const {
        request_to_public,
        request_to_cancel,
        request_to_update,
    } = currentEntry.menu

    if(loading) {
        return <>Loading...</>
    }

    return (
        <>
            <Switch>
                {request_to_public ? <Route path={"/entries/jvar/:entryUUID/requests/publish"} component={Publish}/> : null}
                {request_to_cancel ? <Route path={"/entries/jvar/:entryUUID/requests/cancel"} component={Cancel}/> : null}
                {request_to_update ? <Route path={"/entries/jvar/:entryUUID/requests/update"} component={Update}/> : null}
                <Route path={"/entries/jvar/:entryUUID/requests"} component={List}/>
            </Switch>
        </>
    );
}

export default Requests