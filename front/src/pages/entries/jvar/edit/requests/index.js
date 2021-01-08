import React from 'react'
import {Redirect, Route, Switch} from "react-router-dom"
import List from "./List"
import {useEditingInfo} from "../../../../../hooks/entries/jvar"
import Edit from "./Edit"
import Cancel from "./Cancel"
import Approve from "./Approve";

const Requests = ({match, history}) => {
    const { entryUUID } = match.params

    const {
        loading
    } = useEditingInfo(history, entryUUID)

    if(loading) {
        return <>Loading...</>
    }

    // FIXME 著者か管理者でないと編集できないアクセス制御

    return (
        <>
            <Switch>
                <Route path={"/entries/jvar/:entryUUID/requests/:commentUUID/edit"} component={Edit}/>
                <Route path={"/entries/jvar/:entryUUID/requests/:commentUUID/cancel"} component={Cancel}/>
                <Route path={"/entries/jvar/:entryUUID/requests/:commentUUID/approve"} component={Approve}/>
                <Route path={"/entries/jvar/:entryUUID/requests"} component={List}/>
            </Switch>
        </>
    );
}

export default Requests