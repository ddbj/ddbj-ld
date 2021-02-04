import React, {
    useCallback,
    useMemo
} from 'react'
import {
    useEditingInfo,
    useRequests
} from "../../../../../hooks/entries/jvar"
import ListTable from "../../../../../components/List/ListTable/ListTable"
import { Button } from "react-bootstrap"
import { connect } from "react-redux"
import DefaultColumnFilter from "../../../../../components/Filter/DefaultColumnFilter"
import SelectColumnFilter from "../../../../../components/Filter/SelectColumnFilter"
import {
    useFilters,
    usePagination,
    useTable
} from "react-table"
import { useIsCurator } from "../../../../../hooks/auth"


const List = ({ match, history, currentEntry }) => {
    const { entryUUID } = match.params
    const {
        loading,
    } = useEditingInfo(history, entryUUID)

    const {
        isEditable
    } = useRequests(history, entryUUID)

    const isCurator = useIsCurator()

    const columns = useMemo(() => ([{
        id: 'type',
        Header: "type",
        accessor: 'type',
        Filter: SelectColumnFilter,
        filter: 'equals',
    }, {
        id: 'status',
        Header: "status",
        accessor: 'status',
        Filter: SelectColumnFilter,
        filter: 'equals',
    }, {
        id: 'comment',
        Header: "comment",
        accessor: 'comment',
        Filter: DefaultColumnFilter,
        filter: 'includes',
    }, {
        id: 'author',
        Header: "author",
        accessor: 'author',
        Filter: DefaultColumnFilter,
        filter: 'includes',
    }, {
        id: 'button',
        Header: "",
        accessor: 'button',
        Filter: <div></div>,
        filter: 'equals',
    }]), [currentEntry])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                if(isEditable(cell.row.original.author)) {
                    return (
                        <>
                            <Button
                                variant={"primary"}
                                onClick={() => history.push(`/entries/jvar/${entryUUID}/requests/${cell.row.original.uuid}/edit`)}
                                disabled={false == cell.row.original.is_editable}
                            >
                                Edit
                            </Button>
                            {'　'}
                            <Button
                                variant={"danger"}
                                onClick={() => history.push(`/entries/jvar/${entryUUID}/requests/${cell.row.original.uuid}/cancel`)}
                                disabled={false == cell.row.original.is_cancelable}
                            >
                                Cancel
                            </Button>
                            {'　'}
                            {
                                isCurator
                                ?
                                    <Button
                                        variant={"success"}
                                        onClick={() => history.push(`/entries/jvar/${entryUUID}/requests/${cell.row.original.uuid}/apply`)}
                                        disabled={false == cell.row.original.is_applyable}
                                    >
                                        Apply
                                    </Button>
                                : null
                            }
                        </>
                    )
                } else {
                    return null
                }
            default:
                return <span>{cell.value}</span>
        }
    }, [currentEntry])

    const instance = useTable({
        columns: columns,
        data: currentEntry ? currentEntry.requests : [],
        initialState: {},
    }, useFilters, usePagination)

    if(loading) {
        return <>Loading...</>
    }

    const {
        request,
    } = currentEntry.menu.request_menu

    return (
        <div style={{width: '80%'}}>
            <Button
                variant={"primary"}
                style={{marginTop: 10, marginBottom: 10}}
                disabled={false === request}
                onClick={() => history.push(`/entries/jvar/${entryUUID}/requests/request`)}
            >
                Request
            </Button>
            <ListTable {...instance} renderCell={renderCell}/>
        </div>
    )
}

const mapStateToProps = (state) => {
    return {
        currentEntry: state.entry.currentEntry
    }
}

export default connect(mapStateToProps)(List)