import React from 'react'
import {
    Button,
    Form, FormGroup,
    Input, Label,
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
        curator,
        setCurator,
        isLoading,
        close,
        editIsSubmittable,
        editHandler,
        isCurator,
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
                    {
                        isCurator
                            ?
                            <FormGroup check style={{marginBottom: 10}}>
                                <Label check>
                                    <Input type="checkbox" id="curator" checked={curator} onChange={() => setCurator(document.getElementById("curator").checked)}/>{' '}
                                    Curator Only
                                </Label>
                            </FormGroup>
                            : null
                    }
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