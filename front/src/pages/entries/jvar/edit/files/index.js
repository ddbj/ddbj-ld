import React from 'react';
import {Redirect, Route, Switch} from "react-router-dom";
import Upload from "./Upload"
import Download from "./Download"
import {FileHeader, Header} from "../../components/Header";
import {Button, Nav} from "reactstrap";

const Files = ({match, location, history}) => {
    const { uuid } = match.params

    return (
        <>
            <Nav pills className="mb-4" style={{marginTop: 10}}>
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/upload")}
                    onClick={() => history.push(`/entries/jvar/${uuid}/files/upload`)}
                >
                    Upload
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/download")}
                    onClick={() => history.push(`/entries/jvar/${uuid}/files/download`)}
                >
                    Download
                </Button>
            </Nav>
            <Switch>
                <Route path={"/entries/jvar/:uuid/files/upload"} component={Upload}/>
                <Route path={"/entries/jvar/:uuid/files/download"} component={Download}/>
                <Redirect path="*" to={`/entries/jvar/${uuid}/files/upload`}/>
            </Switch>
        </>
    );
}

export default Files