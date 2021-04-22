import React from 'react'
import {
    Button,
    Form,
    Modal,
    ModalFooter,
    ModalHeader
} from "reactstrap"
import { Link } from "react-router-dom"
import { useComment } from "../../../../../hooks/entries/jvar"

const Delete = ({history, match}) => {
    const { entryUUID, commentUUID } = match.params

    const {
        loading,
        close,
        deleteHandler
    } = useComment(history, entryUUID, commentUUID)

    return (
        <Modal isOpen={true} toggle={loading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/comments`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Delete this comment?
            </ModalHeader>
            <Form onSubmit={deleteHandler}>
                <ModalFooter>
                    <Button
                        disabled={loading}
                        type="submit"
                        color="primary"
                    >
                        {loading ? "Deleting..." : "Delete"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}
export default Delete