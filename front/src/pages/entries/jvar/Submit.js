import React from 'react'
import {Button, Form, Modal, ModalFooter, ModalHeader} from 'reactstrap'

import { useSubmit } from "../../../hooks/entries/jvar"

const Submit = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        isLoading,
        close,
        isSubmittable,
        submitHandler,
    } = useSubmit(history, entryUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <a onClick={isLoading ? null : close}>
                    <i className="fa fa-remove"/>
                </a>
                {'　'}
                Submit Entry?
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalFooter>
                    <Button
                        type="submit"
                        color="primary"
                        onClick={submitHandler}
                        disabled={isLoading || false === isSubmittable}
                    >
                        {isLoading ? "Submitting..." : "Submit"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Submit