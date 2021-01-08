import React from 'react'
import {Link} from 'react-router-dom'
import {Modal, ModalHeader, ModalBody, ModalFooter, Button, Form} from 'reactstrap'

const Apply = ({ match, overwriteDescription, uploadFiles, currentFiles }) => {
    const { entryUUID } = match.params

    return (
        <Modal isOpen={true}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/files`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                <p>Overwrite?</p>
            </ModalHeader>
            <ModalBody>
                <p>{overwriteDescription}</p>
            </ModalBody>
            <ModalFooter>
                <Button
                    type="submit"
                    color="primary"
                    onClick={() => uploadFiles(currentFiles)}
                >
                    Apply
                </Button>
            </ModalFooter>
        </Modal>
    )
}

export default Apply