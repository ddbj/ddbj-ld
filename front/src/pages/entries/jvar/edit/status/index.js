import React from 'react'
import {
    Redirect,
    Route,
    Switch
} from "react-router-dom"

import { useEditingInfo } from "../../../../../hooks/entries/jvar"
import Unsubmitted from "./Unsubmitted"
import Private from "./Private"
import Public from "./Public"
import Suppressed from "./Suppressed"
import Killed from "./Killed"
import Replaced from "./Replaced"


const Status = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        loading,
        toUnsubmitted,
        toPrivate,
        toPublic,
        toSuppressed,
        toKilled,
        toReplaced,
    } = useEditingInfo(history, entryUUID)

    if(loading) {
        return <>Loading...</>
    }

    return (
        <Switch>
            { toUnsubmitted ? <Route path={"/entries/jvar/:entryUUID/status/unsubmitted"} component={Unsubmitted}/> : null }
            { toPrivate ? <Route path={"/entries/jvar/:entryUUID/status/private"} component={Private}/> : null }
            { toPublic ? <Route path={"/entries/jvar/:entryUUID/status/public"} component={Public}/> : null }
            { toSuppressed ? <Route path={"/entries/jvar/:entryUUID/status/suppressed"} component={Suppressed}/> : null }
            { toKilled ? <Route path={"/entries/jvar/:entryUUID/status/killed"} component={Killed}/> : null }
            { toReplaced ? <Route path={"/entries/jvar/:entryUUID/status/replaced"} component={Replaced}/> : null }
            <Redirect path="*" to={`/entries/jvar/${entryUUID}`}/>
        </Switch>
    )
}

export default Status