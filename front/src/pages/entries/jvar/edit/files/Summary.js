import React from 'react'
import {　Link　} from 'react-router-dom'
import {
    Button,
    Modal,
    ModalFooter,
    ModalHeader
} from 'reactstrap'
import { useDeleteFile } from "../../../../../hooks/entries/jvar";

const Summary = ({ match, history }) => {
    const { entryUUID, fileType, fileName } = match.params
    const {
        isLoading,
        close,
        deleteHandler,
    } = useDeleteFile(history, entryUUID, fileType, fileName)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/files`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Summary
            </ModalHeader>
        </Modal>
    )
}

export default Summary