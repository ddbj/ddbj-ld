import React, { useCallback } from 'react'
import { Link } from 'react-router-dom'
import { Modal, ModalHeader, ModalBody } from 'reactstrap'

const Error = ({ match, history }) => {
    const { entryUUID } = match.params
    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/files`), [history])

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/files`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Upload error!
            </ModalHeader>
            <ModalBody>
                <p>
                    The supported file formats are Excel (.xlsx) or Variant Call Format (.vcf).
                </p>
            </ModalBody>
        </Modal>
    )
}

export default Error