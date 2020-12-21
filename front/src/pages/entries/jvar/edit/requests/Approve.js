import React from 'react'
import {Button, Form, Modal, ModalFooter, ModalHeader} from "reactstrap"
import {Link} from "react-router-dom"
import {useComment} from "../../../../../hooks/entries/jvar"

const Approve = ({history, match}) => {
    const { entryUUID, commentUUID } = match.params

    // FIXME 管理者ではないとアクセスできないようにする

    const {
        isLoading,
        close,
        deleteHandler
    } = useComment(history, entryUUID, commentUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/comments`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Delete a comment?
            </ModalHeader>
            <Form onSubmit={deleteHandler}>
                <ModalFooter>
                    <Button
                        disabled={isLoading}
                        type="submit"
                        color="primary"
                    >
                        {isLoading ? "Deleting..." : "Delete"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}
export default Approve