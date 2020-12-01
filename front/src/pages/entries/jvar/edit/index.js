import React from 'react'
import { Header } from '../components/Header'
import {Redirect, Route, Switch} from "react-router-dom"
import Files from './files'
import Comments from "./comments"
import {useEditingInfo} from "../../../../hooks/entries/jvar";

const Edit = ({match, history}) => {
    const { uuid } = match.params

    const {
        loading
    } = useEditingInfo(history, uuid)

    if(loading) {
        return <>Loading...</>
    }

    return (
        <>
            <Route path="/entries/jvar/:uuid" component={Header}/>
            <Switch>
                <Route path={"/entries/jvar/:uuid/files"} component={Files}/>
                {/*<Route path={"/entries/jvar/:uuid/summary"} component={Summary}/>*/}
                <Route path={"/entries/jvar/:uuid/comments"} component={Comments}/>
                <Redirect path="*" to={`/entries/jvar/${uuid}/files`}/>
            </Switch>
        </>
    )
}

export default Edit