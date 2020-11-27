import React, {useCallback, useState} from 'react'
import {Col, Collapse, Row} from 'reactstrap'

export const FileSet = ({fileSet, onUpdate, onDelete, onUploadFiles, onDeleteFile}) => {

    const [isOpen, setIsOpen] = useState(false)
    const [selectedFiles, setSelectedFiles] = useState("")

    const selectFileHandler = useCallback(event => {
        setSelectedFiles(event.target.value)
        const files = [...event.target.files]
        onUploadFiles(files)
    }, [onUploadFiles])

    const deleteFileHandler = useCallback(event => {
        onDeleteFile(event.target.value)
    }, [onDeleteFile])

    return (
        <div className="border-bottom">
            <div className="d-flex bg-light p-2 align-items-center">
                <div className="d-flex align-items-center flex-grow-1 h5 m-0" onClick={() => setIsOpen(!isOpen)}>
                    <i className={`mr-1 la la-angle-${isOpen ? 'down' : 'right'}`}/>
                    <div className="d-flex align-items-center">
                        <input className="form-control mr-2" type="text" value={fileSet.label}
                               onChange={event => onUpdate({...fileSet, label: event.target.value})}/>
                        <div>({fileSet.files.length})</div>
                    </div>
                </div>
                <button onClick={onDelete} className="btn btn-sm btn-danger">削除</button>
            </div>
            <Collapse isOpen={isOpen}>
                <div className="pl-4 pb-2">
                    <ul className="mb-2">
                        {fileSet.files.map(file =>
                            <li key={file.id} className="border-bottom py-2 pr-2">
                                <Row className="align-items-center">
                                    <Col>{file.name}</Col>
                                    <Col className="d-flex justify-content-end">
                                        <button value={file.id} onClick={deleteFileHandler}
                                                className="btn btn-danger btn-sm">削除
                                        </button>
                                    </Col>
                                </Row>
                            </li>
                        )}
                    </ul>
                    <div className="bg-light border p-2 d-flex justify-content-center align-items-center">
                        <label className="btn btn-primary mb-0 mr-2">
                            <input hidden type="file" value={selectedFiles} onChange={selectFileHandler}
                                   multiple/> ファイルを選択
                        </label>
                        <div>あるいはドロップ</div>
                    </div>
                </div>
            </Collapse>
        </div>
    )
}

export const Select = ({fileSets, createFileSet, deleteFileSet, updateFileSet, uploadFiles, deleteFile}) => {
    return (
        <>
            <div className="py-4">
                {fileSets.map(fileSet =>
                    <FileSet
                        key={fileSet.id}
                        fileSet={fileSet}
                        onUpdate={updatedFileset => updateFileSet(updatedFileset)}
                        onDelete={() => deleteFileSet(fileSet.id)}
                        onUploadFiles={files => uploadFiles(fileSet.id, files)}
                        onDeleteFile={fileId => deleteFile(fileSet.id, fileId)}/>
                )}
                <div className="p-4 d-flex justify-content-center">
                    <button onClick={createFileSet} className="btn btn-default">ファイルセットを追加</button>
                </div>
            </div>
        </>
    )
}

export default Select