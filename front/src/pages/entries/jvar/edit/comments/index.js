import React from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import List from "./List"
import Post from "./Post";
import {useEditingInfo} from "../../../../../hooks/entries/jvar";

const Comments = ({match, location, history}) => {
    const { uuid } = match.params

    const {
        loading
    } = useEditingInfo(history, uuid)

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
            {/*        onClick={() => history.push(`/entries/jvar/${uuid}/files/upload`)}*/}
            {/*    >*/}
            {/*        Upload*/}
            {/*    </Button>*/}
            {/*    {'　'}*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.endsWith("/download")}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${uuid}/files/download`)}*/}
            {/*    >*/}
            {/*        Download*/}
            {/*    </Button>*/}
            {/*</Nav>*/}
            <Switch>
                <Route path={"/entries/jvar/:uuid/comments/post"} component={Post}/>
                <Route path={"/entries/jvar/:uuid/comments"} component={List}/>
                <Redirect path="*" to={`/search`}/>
            </Switch>
        </>
    );
}

export default Comments