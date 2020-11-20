import React from 'react'
import {useIntl} from "react-intl";
import {useEntries} from "../../../hooks/entries/jvar";
import ListTable from "../../project/components/List/ListTable";
import {Button} from "react-bootstrap";

const List = ({ history }) => {
    const intl = useIntl()
    const { renderCell, instance } = useEntries(history)
    // FIXME 検索ボックス
    // FIXME 削除したとき、2ページ目以降だと1ページ目に戻ってしまう(削除モーダルにURI持たさない方向のほうが良いかも

    return (
        <>
            <div style={{display: "flex", justifyContent: "space-between"}}>
                <h2>JVar</h2>
                <Button
                    style={{height: 35}}
                    onClick={() => history.push("/entries/jvar/create")}
                >
                    {intl.formatMessage({id: 'common.button.create'})}
                </Button>
            </div>
            <ListTable {...instance} renderCell={renderCell}/>
        </>
    )
}

export default List