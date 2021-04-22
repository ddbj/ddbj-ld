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

const Apply = ({ match, history }) => {
    const { entryUUID, requestUUID } = match.params

    const {
        isLoading,
        close,
        applyHandler,
    } = useRequests(history, entryUUID, requestUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/requests`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Apply this request?
            </ModalHeader>
            <Form onSubmit={applyHandler}>
                <ModalFooter>
                    <Button
                        disabled={isLoading}
                        type="submit"
                        color="primary"
                    >
                        {isLoading ? "Applying..." : "Apply"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Apply