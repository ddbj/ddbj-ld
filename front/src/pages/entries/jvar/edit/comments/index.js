import React from 'react'
import {Redirect, Route, Switch} from "react-router-dom"
import List from "./List"
import Post from "./Post"
import {useEditingInfo} from "../../../../../hooks/entries/jvar"
import Edit from "./Edit";
import Delete from "./Delete";

const Comments = ({match, location, history}) => {
    const { entryUUID } = match.params

    const {
        loading
    } = useEditingInfo(history, entryUUID)

    if(loading) {
        return <>Loading...</>
    }

    return (
        <>
            {/*<Nav pills className="mb-4" style={{marginTop: 10}}>*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.endsWith("/upload")}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${entryUUID}/files/upload`)}*/}
            {/*    >*/}
            {/*        Upload*/}
            {/*    </Button>*/}
            {/*    {'　'}*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.endsWith("/download")}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${entryUUID}/files/download`)}*/}
            {/*    >*/}
            {/*        Download*/}
            {/*    </Button>*/}
            {/*</Nav>*/}
            <Switch>
                <Route path={"/entries/jvar/:entryUUID/comments/post"} component={Post}/>
                <Route path={"/entries/jvar/:entryUUID/comments/:commentUUID/edit"} component={Edit}/>
                <Route path={"/entries/jvar/:entryUUID/comments/:commentUUID/delete"} component={Delete}/>
                <Route path={"/entries/jvar/:entryUUID/comments"} component={List}/>
                <Redirect path="*" to={`/search`}/>
            </Switch>
        </>
    );
}

export default Comments