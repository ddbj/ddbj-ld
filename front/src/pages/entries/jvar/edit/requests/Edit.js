import React from 'react'
import {
    Modal,
    ModalFooter,
    Button,
    ModalBody,
    Input,
    Form, ModalHeader
} from 'reactstrap'
import { useRequests } from "../../../../../hooks/entries/jvar"
import { Link } from "react-router-dom";
import FormGroup from "react-bootstrap/FormGroup";
import Label from "reactstrap/es/Label";
import {connect} from "react-redux";

const Edit = ({ match, history }) => {
    const { entryUUID, requestUUID } = match.params

    const {
        comment,
        setComment,
        isLoading,
        close,
        editIsSubmittable,
        editHandler,
    } = useRequests(history, entryUUID, requestUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <Form onSubmit={editHandler}>
                <ModalHeader>
                    <Link to={`/entries/jvar/${entryUUID}/requests`} className="p-2 mr-2 text-secondary">
                        <i className="fa fa-remove"/>
                    </Link>
                    Request
                </ModalHeader>
                <ModalBody>
                    <Label>Comment:</Label>
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

const mapStateToProps = (state) => {
    return {
        currentEntry: state.entry.currentEntry
    }
}

export default connect(mapStateToProps)(Edit)