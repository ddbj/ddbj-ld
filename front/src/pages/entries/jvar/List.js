import React from 'react'
import { useEntries } from "../../../hooks/entries/jvar"
import ListTable from "../../project/components/List/ListTable"
import { Button } from "react-bootstrap"
import { usePagination, useSortBy, useTable } from "react-table"
import { connect } from "react-redux"

const List = ({ history, entries }) => {
    const {
        renderCell,
        columns,
        loading,
    } = useEntries(history)
    // FIXME 検索ボックス
    // FIXME 削除したとき、2ページ目以降だと1ページ目に戻ってしまう(削除モーダルにURI持たさない方向のほうが良いかも

    const instance = useTable({
        columns,
        data: entries ? entries : [],
    }, useSortBy, usePagination)

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
            <ListTable {...instance} renderCell={renderCell}/>
        </>
    )
}

const mapStateToProps = (state) => {
    return {
        entries: state.entry.entries,
    }
}

export default connect(mapStateToProps)(List)