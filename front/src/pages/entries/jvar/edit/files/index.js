import React from 'react'
import {Route, Switch} from "react-router-dom"
import {useEditingInfo, useFiles} from "../../../../../hooks/entries/jvar"
import Error from "./Error"
import Loading from "./Loading"
import ListTable from "../../../../project/components/List/ListTable"

const Files = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        getRootProps,
        getInputProps,
        loading,
        onSelect
    } = useFiles(history, entryUUID)

    const {
        fileRenderCell,
        fileInstance
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
                type="file" onChange={onSelect}
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
                <Route path={"/entries/jvar/:entryUUID/files/error"} component={Error}/>
                <Route path={"/entries/jvar/:entryUUID/files/loading"} component={Loading}/>
            </Switch>
        </div>
    )
}

export default Files