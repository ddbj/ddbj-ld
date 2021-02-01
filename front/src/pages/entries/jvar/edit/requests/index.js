import React from 'react'
import { Route, Switch } from "react-router-dom"
import List from "./List"
import { useEditingInfo } from "../../../../../hooks/entries/jvar"
import Request from "./Request";

const Requests = ({match, history}) => {
    const { entryUUID } = match.params

    const {
        loading,
        currentEntry
    } = useEditingInfo(history, entryUUID)

    const {
        request,
    } = currentEntry ? currentEntry.menu.request_menu : { request: false }

    if(loading) {
        return <>Loading...</>
    }

    return (
        <>
            <Switch>
                {request ? <Route path={"/entries/jvar/:entryUUID/requests/request"} component={Request}/> : null}
                <Route path={"/entries/jvar/:entryUUID/requests"} component={List}/>
            </Switch>
        </>
    );
}

export default Requests