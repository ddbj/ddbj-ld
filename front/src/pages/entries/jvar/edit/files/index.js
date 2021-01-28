import React, { useCallback, useMemo } from 'react'
import {Redirect, Route, Switch} from "react-router-dom"
import {
    useFilters,
    usePagination,
    useTable
} from "react-table"
import { connect } from "react-redux"
import { Button } from "react-bootstrap"

import { useFiles } from "../../../../../hooks/entries/jvar"
import Error from "./Error"
import Loading from "./Loading"
import Apply from "./Apply"
import ListTable from "../../../../../components/List/ListTable/ListTable"
import Delete from "./Delete"
import DefaultColumnFilter from "../../../../../components/Filter/DefaultColumnFilter"
import SelectColumnFilter from "../../../../../components/Filter/SelectColumnFilter"


const Files = ({ currentEntry, match, history }) => {
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
        downloadHandler,
    } = useFiles(history, entryUUID)

    const fileColumns = useMemo(() => ([{
        id: 'name',
        Header: "name",
        accessor: 'name',
        Filter: DefaultColumnFilter,
        filter: 'includes',
    }, {
        id: 'type',
        Header: "type",
        accessor: 'type',
        Filter: SelectColumnFilter,
        filter: 'equals',
    }, {
        id: 'validation_status',
        Header: "validation status",
        accessor: 'validation_status',
        Filter: SelectColumnFilter,
        filter: 'equals',
    }, {
        id: 'button',
        Header: '',
        accessor: 'button',
        Filter: <div></div>,
        filter: 'equals',
    }]), [])

    const fileRenderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                return (
                    <div style={{width: 275}}>
                        <Button
                            variant={"primary"}
                            size={"sm"}
                            onClick={() => downloadHandler(cell.row.original.type, cell.row.original.name)}
                        >
                            Download
                        </Button>
                        {'　'}
                        <Button
                            variant={"info"}
                            onClick={null}
                            disabled={true}
                        >
                            History
                        </Button>
                        {'　'}
                        <Button
                            variant={"danger"}
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/files/${cell.row.original.type}/${cell.row.original.name}/delete`)}
                        >
                            Delete
                        </Button>
                    </div>
                )
            case 'name':
                return <span>{cell.value}</span>
            default:
                return <div style={{width :70}}>{cell.value}</div>
        }
    }, [history])

    const fileInstance = useTable({
        columns: fileColumns,
        data: currentEntry ? currentEntry.files : [],
        initialState: {},
    }, useFilters, usePagination)

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

const mapStateToProps = (state) => {
    return {
        currentEntry: state.entry.currentEntry
    }
}

export default connect(mapStateToProps)(Files)