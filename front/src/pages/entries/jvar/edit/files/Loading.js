import React from 'react'
import {Link} from 'react-router-dom'
import { Modal, ModalHeader } from 'reactstrap'

const Loading = ({ match }) => {
    const { entryUUID } = match.params

    return (
        <Modal isOpen={true}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/files`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Uploading...
            </ModalHeader>
        </Modal>
    )
}

export default Loading