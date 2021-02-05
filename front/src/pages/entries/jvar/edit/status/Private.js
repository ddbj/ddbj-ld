import React from 'react'
import {Link} from 'react-router-dom'
import {Modal, ModalHeader, ModalFooter, Button} from 'reactstrap'
import { useStatus } from "../../../../../hooks/entries/jvar"

const Private = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        loading,
        close,
        submitHandler,
    } = useStatus(history, entryUUID, "private")

    return (
        <Modal isOpen={true} toggle={loading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                To private?
            </ModalHeader>
            <ModalFooter>
                <Button
                    type="submit"
                    color="primary"
                    onClick={submitHandler}
                    disabled={loading}
                >
                    {loading ? "Updating..." : "Apply"}
                </Button>
            </ModalFooter>
        </Modal>
    )
}

export default Private