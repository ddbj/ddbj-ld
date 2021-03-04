import React, { useCallback } from 'react'
import { Link } from 'react-router-dom'
import {
    Modal,
    ModalHeader,
    ModalBody
} from 'reactstrap'

const Error = ({ match, history, errorTitle, errorDescription  }) => {
    const { entryUUID } = match.params
    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/files`), [history])

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/files`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                {errorTitle}
            </ModalHeader>
            <ModalBody>
                <p>
                    {errorDescription}
                </p>
            </ModalBody>
        </Modal>
    )
}

export default Error