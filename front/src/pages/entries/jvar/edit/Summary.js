import React from 'react'
import {useEditingInfo} from "../../../../hooks/entries/jvar";
import ListTable from "../../../project/components/List/ListTable";

const Summary = ({match, history}) => {
    const { uuid } = match.params
    const { validationRenderCell, validationInstance } = useEditingInfo(uuid)

    return (
        <>
            <div style={{height: 200, width: '80%'}}>
                <ListTable {...validationInstance} renderCell={validationRenderCell}/>
            </div>
        </>
    )
}

export default Summary