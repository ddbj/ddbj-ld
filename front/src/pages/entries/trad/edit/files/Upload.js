import React from 'react'
import DropZone from "react-dropzone";

const Upload = ({match, history}) => {
    // FIXME hooksでやる形とする https://github.com/react-dropzone/react-dropzone
    return (
        <div style={{height: 200, width: '100%'}}>
            <DropZone
                onDrop={null}
                style={{
                    border: '2px dashed #ccc',
                    width: '80%',
                    height: 200,
                    marginTop: 10,
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                }}
            >
                <p>Drug and drop here, Excel files or VCF files.</p>
            </DropZone>
        </div>
    )
}

export default Upload