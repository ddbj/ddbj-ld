import React from 'react'
import { Link } from 'react-router-dom'
import {
    Modal,
    ModalHeader,
    ModalFooter,
    Button,
    Form
} from 'reactstrap'
import { useRequests } from "../../../../../hooks/entries/jvar"

const Cancel = ({ match, history }) => {
    const { entryUUID, requestUUID } = match.params

    const {
        isLoading,
        close,
        cancelHandler,
    } = useRequests(history, entryUUID, requestUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/requests`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Cancel a request?
            </ModalHeader>
            <Form onSubmit={cancelHandler}>
                <ModalFooter>
                    <Button
                        disabled={isLoading}
                        type="submit"
                        color="primary"
                    >
                        {isLoading ? "Canceling..." : "Cancel"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Cancel