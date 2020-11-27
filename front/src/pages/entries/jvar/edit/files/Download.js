import React from 'react'
import {useEditingInfo} from "../../../../../hooks/entries/jvar"
import ListTable from "../../../../project/components/List/ListTable"

const Download = ({match, history}) => {
    const { uuid } = match.params
    const { fileRenderCell , fileInstance } = useEditingInfo(uuid)

    return (
        <div style={{height: 200, width: '80%'}}>
            <ListTable {...fileInstance} renderCell={fileRenderCell}/>
        </div>
    )
}

export default Download