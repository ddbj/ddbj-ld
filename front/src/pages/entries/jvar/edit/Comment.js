import React from 'react'
import {useEditingInfo} from "../../../../hooks/entries/jvar";
import ListTable from "../../../project/components/List/ListTable";
import {Button} from "react-bootstrap";


const Comment = ({match, history}) => {
    const { uuid } = match.params
    const { commentRenderCell , commentInstance } = useEditingInfo(uuid)

    return (
        <div style={{height: 200, width: '80%'}}>
            <Button  variant={"primary"} style={{marginTop: 10, marginBottom: 10}}>Comment</Button>
            <ListTable {...commentInstance} renderCell={commentRenderCell}/>
        </div>
    )
}

export default Comment