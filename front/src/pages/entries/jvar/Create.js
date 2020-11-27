<<<<<<< HEAD
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

=======
import React, {useCallback, useMemo, useState} from 'react'
import {Link} from 'react-router-dom'
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap'

import {RequiredBadge} from '../../../components/JVar/Form'
import {useIntl} from "react-intl";
import {useDispatch} from "react-redux"
import { createEntry } from "../../../actions/entry"

const Create = ({history}) => {
    const [title, setTitle] = useState('')
    const [setDescription, description] = useState('')

    const close = useCallback(() => history.push(`/entries/jvar`), [history])

    const isSubmittable = useMemo(() => {
        return !!title
    }, [title, description])

>>>>>>> 差分修正
    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
<<<<<<< HEAD
        setLoading(true)

        const type = document.getElementById("snp").checked ? "SNP" : "SV"
        dispatch(createEntry(history, type, setLoading))
    }, [close])

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
=======
        if (!isSubmittable) return

        dispatch(createEntry(history, title, description))
        close()
    }, [isSubmittable, close, title, description])

    const intl = useIntl()

    return (
        <Modal isOpen={true} toggle={close}>
>>>>>>> 差分修正
            <ModalHeader>
                <Link to={`/entries/jvar`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
<<<<<<< HEAD
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
=======
                {intl.formatMessage({id: 'entry.create.title'})}
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalBody>
                    <FormGroup>
                        <Label>{intl.formatMessage({id: 'entry.create.label.title'})}{' '}<RequiredBadge/></Label>
                        <Input type="text" required value={title} onChange={event => setTitle(event.target.value)}/>
                    </FormGroup>
                    <FormGroup>
                        <Label>{intl.formatMessage({id: 'entry.create.label.description'})}</Label>
                        <Input type="textarea" value={description} onChange={event => setDescription(event.target.value)}/>
                    </FormGroup>
                </ModalBody>
                <ModalFooter>
                    <Button disabled={!isSubmittable} type="submit" color="primary">{intl.formatMessage({id: 'common.button.create'})}</Button>
>>>>>>> 差分修正
                </ModalFooter>
            </Form>
        </Modal>
    )
}

<<<<<<< HEAD
export default Create
=======
export default Create
>>>>>>> 差分修正
