import React from 'react'
import { Link } from 'react-router-dom'
import {
    Button,
    Form,
    Modal,
    ModalFooter,
    ModalHeader
} from 'reactstrap'
import { useDeleteEntry } from "../../../hooks/entries/jvar"

const Delete = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        close,
        loading,
        deleteHandler,
    } = useDeleteEntry(history, entryUUID)

    return (
        <Modal isOpen={true} toggle={loading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Delete this entry?
            </ModalHeader>
            <ModalFooter>
                <Button
                    type="submit"
                    color="primary"
                    onClick={deleteHandler}
                    disabled={loading}
                >
                    {loading ? "Deleting..." : "Delete"}
                </Button>
            </ModalFooter>
        </Modal>
    )
}

export default Delete