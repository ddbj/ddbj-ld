import React, { useCallback, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import {
    Button,
    Form,
    FormGroup,
    Input,
    Label,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader,
    Col
} from 'reactstrap'

import { useDispatch } from "react-redux"
import { createEntry } from "../../../actions/entry"

const Create = ({history}) => {
    const [isLoading, setLoading] = useState(false)

    const close = useCallback(() => history.push(`/entries/jvar`), [history])

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
        setLoading(true)

        const type = document.getElementById("snp").checked ? "SNP" : "SV"
        dispatch(createEntry(history, type, setLoading))
    }, [close])

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Create a new entry
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalBody>
                    <Form>
                        <FormGroup tag="fieldset" row>
                            <Col sm={10}>
                                <Label>Entry Type</Label>
                                <FormGroup check>
                                    <Label check>
                                        <Input type="radio" name="type" checked id="snp"/>{' '}
                                        SNP
                                    </Label>
                                </FormGroup>
                                <FormGroup check>
                                    <Label check>
                                        <Input type="radio" name="type" id="sv"/>{' '}
                                        SV
                                    </Label>
                                </FormGroup>
                            </Col>
                        </FormGroup>
                    </Form>
                </ModalBody>
                <ModalFooter>
                    <Button
                        disabled={isLoading}
                        type="submit"
                        color="primary"
                    >
                        {isLoading ? "Creating..." : "Create"}
                    </Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Create
