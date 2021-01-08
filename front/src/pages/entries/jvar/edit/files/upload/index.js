import React from 'react'
import { useFiles } from '../../../../../../hooks/entries/jvar'
import { Redirect, Route, Switch } from "react-router-dom"
import Loading from "./Loading"
import Error from "./Error"

const Upload = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        getRootProps,
        getInputProps,
        loading
    } = useFiles(history, entryUUID)

    if(loading) {
        return <>Loading...</>
    }

    return (
        <div
            style={{
                height: 200,
                width: '100%'
            }}
            {...getRootProps()}
        >
            <div
                style={{
                    border: '2px dashed #ccc',
                    width: '80%',
                    height: 200,
                    marginTop: 10,
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                }}
            >
                <input {...getInputProps()} />
                <p>Drug and drop here, Excel files or VCF files.</p>
            </div>
            <Switch>
                <Route path={"/entries/jvar/:entryUUID/files/upload/error"} component={Error}/>
                <Route path={"/entries/jvar/:entryUUID/files/upload/loading"} component={Loading}/>
                <Redirect path="*" to={`/entries/jvar/${entryUUID}/files/upload`}/>
            </Switch>
        </div>
    )
}

export default Upload