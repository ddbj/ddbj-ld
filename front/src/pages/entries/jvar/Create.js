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

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return

        dispatch(createEntry(history, title, description))
        close()
    }, [isSubmittable, close, title, description])

    const intl = useIntl()

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/entries/jvar`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
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
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Create