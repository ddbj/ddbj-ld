import React, {useCallback, useMemo} from 'react'
import { useEditingInfo } from "../../../../../hooks/entries/jvar"
import ListTable from "../../../../../components/List/ListTable/ListTable"
import { Button } from "react-bootstrap"
import {connect} from "react-redux";
import {useFilters, usePagination, useTable} from "react-table";
import DefaultColumnFilter from "../../../../../components/Filter/DefaultColumnFilter";
import SelectColumnFilter from "../../../../../components/Filter/SelectColumnFilter";


const List = ({ match, history, currentEntry }) => {
    const { entryUUID } = match.params
    const { isEditable } = useEditingInfo(history, entryUUID)

    const commentColumns = useMemo(() => ([{
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
        id: 'visibility',
        Header: "visibility",
        accessor: 'visibility',
        Filter: SelectColumnFilter,
        filter: 'equals',
    }, {
        id: 'button',
        Header: "",
        accessor: 'button',
        Filter: <div></div>,
        filter: 'equals',
    }]), [currentEntry])

    const commentRenderCell = useCallback(cell => {
        switch (cell.column.id) {
            case 'button':
                if(isEditable(cell.row.original.author)) {
                    return (
                        <>
                            <Button
                                variant={"primary"}
                                onClick={() => history.push(`/entries/jvar/${entryUUID}/comments/${cell.row.original.uuid}/edit`)}
                            >
                                Edit
                            </Button>
                            {'　'}
                            <Button
                                variant={"danger"}
                                onClick={() => history.push(`/entries/jvar/${entryUUID}/comments/${cell.row.original.uuid}/delete`)}
                            >
                                Delete
                            </Button>
                        </>
                    )
                } else {
                    return null
                }
            default:
                return <span>{cell.value}</span>
        }
    }, [currentEntry])

    const commentInstance = useTable({
        columns: commentColumns,
        data: currentEntry ? currentEntry.comments : [],
        initialState: {},
    }, useFilters, usePagination)

    return (
        <div style={{width: '80%'}}>
            <Button
                variant={"primary"}
                style={{marginTop: 10, marginBottom: 10}}
                onClick={() => history.push(`/entries/jvar/${entryUUID}/comments/post`)}
            >
                Post comment
            </Button>
            <ListTable {...commentInstance} renderCell={commentRenderCell}/>
        </div>
    )
}

const mapStateToProps = (state) => {
    return {
        currentEntry: state.entry.currentEntry
    }
}

export default connect(mapStateToProps)(List)