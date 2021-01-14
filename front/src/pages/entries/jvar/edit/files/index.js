import React from 'react'
import {Redirect, Route, Switch} from "react-router-dom"
import { useEditingInfo, useFiles } from "../../../../../hooks/entries/jvar"
import Error from "./Error"
import Loading from "./Loading"
import Apply from "./Apply"
import ListTable from "../../../../../components/List/ListTable/ListTable"
import Delete from "./Delete"

const Files = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        getRootProps,
        getInputProps,
        loading,
        onSelect,
        hasError,
        uploading,
        overwriting,
        errorTitle,
        errorDescription,
        overwriteDescription,
        uploadFiles,
        currentFiles,
    } = useFiles(history, entryUUID)

    const {
        fileRenderCell,
        fileInstance,
    } = useEditingInfo(history, entryUUID)

    if(loading) {
        return <>Loading...</>
    }

    return (
        <div
            style={{
                marginTop: 20,
                width: '100%'
            }}
        >
            <input
                id="files"
                type="file"
                onChange={onSelect}
                multiple
            />
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
                {...getRootProps()}
            >
                <input {...getInputProps()} />
                <div>
                    <p>Drug and drop here, Metadata(Excel) or VCF files.</p>
                    <p>You need one Metadata and one or more VCFs to validate and submit this entry.</p>
                </div>
            </div>
            <div style={{
                marginTop: 20,
                width: '80%',
            }}
            >
                <ListTable {...fileInstance} renderCell={fileRenderCell}/>
            </div>
            <Switch>
                {
                    hasError ? <Route path={"/entries/jvar/:entryUUID/files/error"} component={
                        (props) => <Error match={props.match}  history={props.history} errorTitle={errorTitle} errorDescription={errorDescription}/>}/>
                        : null
                }
                {uploading ? <Route path={"/entries/jvar/:entryUUID/files/loading"} component={Loading}/> : null}
                {overwriting ? <Route path={"/entries/jvar/:entryUUID/files/apply"} component={(props) => <Apply match={props.match} overwriteDescription={overwriteDescription} uploadFiles={uploadFiles} currentFiles={currentFiles}/>} /> : null}
                <Route path={"/entries/jvar/:entryUUID/files/:fileType/:fileName/delete"} component={Delete}/>
                <Redirect path="*" to={`/entries/jvar/${entryUUID}/files`}/>
            </Switch>
        </div>
    )
}

export default Files