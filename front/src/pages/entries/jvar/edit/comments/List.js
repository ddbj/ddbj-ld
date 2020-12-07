import React from 'react'
import {useEditingInfo} from "../../../../../hooks/entries/jvar";
import ListTable from "../../../../project/components/List/ListTable";
import {Button} from "react-bootstrap";


const List = ({match, history}) => {
    const { entryUUID } = match.params
    const { commentRenderCell , commentInstance } = useEditingInfo(history, entryUUID)

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

export default List