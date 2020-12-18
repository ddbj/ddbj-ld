import React from 'react'
import { useEntries } from "../../../hooks/entries/jvar"
import ListTable from "../../project/components/List/ListTable"
import { Button } from "react-bootstrap"

const List = ({ history }) => {
    const { renderCell, instance } = useEntries(history)
    // FIXME 検索ボックス
    // FIXME 削除したとき、2ページ目以降だと1ページ目に戻ってしまう(削除モーダルにURI持たさない方向のほうが良いかも

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

export default List