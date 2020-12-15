import React, {useCallback, useState} from 'react'
import {Button, Form, Modal, ModalFooter, ModalHeader} from 'reactstrap'

import {useDispatch} from "react-redux"
import { deleteEntry } from "../../../actions/entry"
import {useValidate} from "../../../hooks/entries/jvar";

const Validate = ({match, history}) => {
    const { entryUUID } = match.params

    const {
        isLoading,
        close,
        validateIsSubmittable,
        validateHandler,
    } = useValidate(history, entryUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <a onClick={isLoading ? null : close}>
                    <i className="fa fa-remove"/>
                </a>
                {'　'}
                Validate Entry
            </ModalHeader>
            <Form onSubmit={validateHandler}>
                <ModalFooter>
                    <Button
                        type="submit"
                        color="primary"
                        onClick={validateHandler}
                        disabled={isLoading || false === validateIsSubmittable}
                    >
                        {isLoading ? "Validating..." : "Validate"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Validate