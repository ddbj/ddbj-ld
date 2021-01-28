import React from 'react'
import { useEditingInfo } from "../../../../../hooks/entries/jvar"

const Summary = ({match, history}) => {
    const { entryUUID } = match.params

    const {
        loading,
        currentEntry,
    } = useEditingInfo(history, entryUUID)

    if(loading) {
        return <>Loading...</>
    }

    return (
        <div style={{width: '80%', marginTop: 10}}>
            Under Construction...
        </div>
    );
}

export default Summary