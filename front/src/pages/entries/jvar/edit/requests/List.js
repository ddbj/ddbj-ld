import React from 'react'
import {useEditingInfo} from "../../../../../hooks/entries/jvar";
import ListTable from "../../../../project/components/List/ListTable";


const List = ({match, history}) => {
    const { entryUUID } = match.params
    // FIXME
    const { commentRenderCell , commentInstance } = useEditingInfo(history, entryUUID)

    return (
        <div style={{width: '80%'}}>
            <ListTable {...commentInstance} renderCell={commentRenderCell}/>
        </div>
    )
}

export default List