import React, { useCallback, useMemo } from 'react'
import { Button } from "react-bootstrap"
import {
    useFilters,
    usePagination,
    useTable
} from "react-table"
import { connect } from "react-redux"

import { useEntries } from "../../../hooks/entries/jvar"
import ListTable from "../../../components/List/ListTable/ListTable"
import DefaultColumnFilter from "../../../components/Filter/DefaultColumnFilter"
import SelectColumnFilter from "../../../components/Filter/SelectColumnFilter"

const List = ({ entries, history }) => {
    const {
        loading,
    } = useEntries(history)

    const columns = useMemo(() => ([{
        id: 'uuid',
        Header: 'uuid',
        accessor: 'uuid',
        Filter: DefaultColumnFilter,
        filter: 'includes',
    }, {
        id: 'label',
        Header: 'label',
        accessor: 'label',
        Filter: DefaultColumnFilter,
        filter: 'includes',
    }, {
        id: 'type',
        Header: 'type',
        accessor: 'type',
        Filter: SelectColumnFilter,
        filter: 'equals',
    }, {
        id: 'status',
        Header: 'status',
        accessor: 'status',
        Filter: SelectColumnFilter,
        filter: 'equals',
    },  {
        id: 'active_request',
        Header: 'active request',
        accessor: 'active_request',
        Filter: SelectColumnFilter,
        filter: 'equals',
    }, {
        id: 'button',
        Header: '',
        accessor: 'button',
        Filter: <div></div>,
        filter: 'equals',
    }]), [])

    const renderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                return (
                    <div style={{ width: 150 }}>
                        <Button onClick={() => history.push(`/entries/jvar/${cell.row.original.uuid}`)}>Edit</Button>
                        {'　'}
                        {cell.row.original.isDeletable
                            ? <Button variant={"danger"} onClick={() => history.push(`/entries/jvar/${cell.row.original.uuid}/delete`)}>Delete</Button>
                            : null
                        }
                    </div>
                )
            case 'uuid':
                return <div style={{ width: 290 }}>{cell.value}</div>
            default:
                return <div style={{ width: 100 }}>{cell.value}</div>
        }
    }, [])

    const instance = useTable({
        columns,
        data: entries ? entries : [],
    }, useFilters, usePagination)

    if(loading) {
        return <div>Loading...</div>
    }

    return (
        <>
            <div style={{display: "flex"}}>
                <h2>JVar</h2>
                {'　'}
                <Button
                    style={{height: 32}}
                    onClick={() => history.push("/entries/jvar/create")}
                >
                    Create Entry
                </Button>
            </div>
            {instance ?  <ListTable {...instance} renderCell={renderCell}/> : null}
        </>
    )
}

const mapStateToProps = (state) => {
    return {
        entries: state.entry.entries
    }
}

export default connect(mapStateToProps)(List)