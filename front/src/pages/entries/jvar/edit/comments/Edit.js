import React from 'react'
import {
    Button,
    Form,
    Input,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader
} from "reactstrap"
import { Link } from "react-router-dom"
import { useComment } from "../../../../../hooks/entries/jvar"

const Edit = ({ history, match }) => {
    const {
        entryUUID,
        commentUUID
    } = match.params

    const {
        comment,
        setComment,
        isLoading,
        close,
        editIsSubmittable,
        editHandler
    } = useComment(history, entryUUID, commentUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/comments`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Comment
            </ModalHeader>
            <Form onSubmit={editHandler}>
                <ModalBody>
                    <Input type="textarea" value={comment} onChange={event => setComment(event.target.value)}/>
                </ModalBody>
                <ModalFooter>
                    <Button
                        disabled={isLoading || !editIsSubmittable}
                        type="submit"
                        color="primary"
                    >
                        {isLoading ? "Updating..." : "Update"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}
export default Edit