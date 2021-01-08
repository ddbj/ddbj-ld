import React from 'react'
import {Redirect, Route, Switch} from "react-router-dom"
import Upload from "./upload"
import Download from "./Download"
import {Button, Nav} from "reactstrap"
import Error from "./upload/Error";
import Loading from "./upload/Loading";

const Files = ({match, location, history}) => {
    const { entryUUID } = match.params

    return (
        <>
            <Nav pills className="mb-4" style={{marginTop: 10}}>
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/upload")}
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/files/upload`)}
                >
                    Upload
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/download")}
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/files/download`)}
                >
                    Download
                </Button>
            </Nav>
            <Switch>
                <Route path={"/entries/jvar/:entryUUID/files/upload"} component={Upload}/>
                <Route path={"/entries/jvar/:entryUUID/files/download"} component={Download}/>
                <Redirect path="*" to={`/entries/jvar/${entryUUID}/files/upload`}/>
            </Switch>
        </>
    );
}

export default Files