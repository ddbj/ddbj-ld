import React from 'react'
import {useEditingInfo} from "../../../../../hooks/entries/jvar";
import ListTable from "../../../../project/components/List/ListTable";


const List = ({match, history}) => {
    const { entryUUID } = match.params
    // FIXME
    const { commentRenderCell , commentInstance } = useEditingInfo(history, entryUUID)

    return (
        <div style={{width: '80%', marginTop: 10}}>
            {/*<ListTable {...commentInstance} renderCell={commentRenderCell}/>*/}
            Under Construction...
        </div>
    )
}

export default List