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

const Request = ({ match, history, currentEntry }) => {
    const { entryUUID } = match.params

    const {
        type,
        setType,
        comment,
        setComment,
        isLoading,
        setLoading,
        close,
        requestIsSubmittable,
        requestHandler,
    } = useRequests(history, entryUUID)

    const {
        request_to_public,
        request_to_update,
        request_to_cancel,
    } = currentEntry.menu.request_menu

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <Form onSubmit={requestHandler}>
                <ModalHeader>
                    <Link to={`/entries/jvar/${entryUUID}/requests`} className="p-2 mr-2 text-secondary">
                        <i className="fa fa-remove"/>
                    </Link>
                    Request
                </ModalHeader>
                <ModalBody>
                    <FormGroup id="requests">
                        <Label>Type:</Label>
                        <FormGroup check style={{marginLeft : 30}}>
                            <Label check>
                                <Input
                                    type="radio"
                                    name="request"
                                    disabled={false === request_to_public}
                                    onChange={() => setType("public")}
                                />
                                {' '}
                                to public
                            </Label>
                        </FormGroup>
                        <FormGroup check style={{marginLeft : 30}}>
                            <Label check>
                                <Input
                                    type="radio"
                                    name="request"
                                    disabled={false === request_to_cancel}
                                    onChange={() => setType("cancel")}
                                />
                                {' '}
                                to cancel
                            </Label>
                        </FormGroup>
                        <FormGroup check style={{marginLeft : 30}}>
                            <Label check>
                                <Input
                                    type="radio"
                                    name="request"
                                    disabled={false === request_to_update}
                                    onChange={() => setType("update")}
                                />
                                {' '}
                                to update
                            </Label>
                        </FormGroup>
                    </FormGroup>
                    <Label>Comment:</Label>
                    <Input type="textarea" value={comment} onChange={event => setComment(event.target.value)}/>
                </ModalBody>
                <ModalFooter>
                    <Button
                        disabled={isLoading || !requestIsSubmittable}
                        type="submit"
                        color="primary"
                    >
                        {isLoading ? "Requesting..." : "Request"}
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

export default connect(mapStateToProps)(Request)