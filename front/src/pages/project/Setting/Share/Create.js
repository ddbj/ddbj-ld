import React, {useCallback, useMemo, useState} from 'react'
import {Link} from 'react-router-dom'
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap'

import {RequiredBadge} from '../../../../components/JVar/Form'
import {useIntl} from "react-intl";
import {useDispatch} from "react-redux"
import { createAccessGrant } from '../../../../actions/project'

const Create = ({match, history}) => {
    const {id} = match.params

    const [label, setLabel] = useState('')
    const [expireDate, setExpireDate] = useState('')

    const close = useCallback(() => history.push(`/me/project/${id}/setting/share`), [history, id])

    const isSubmittable = useMemo(() => {
        return !!label && !!expireDate
    }, [label, expireDate])

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return

        dispatch(createAccessGrant(id, label, expireDate))
        close()
    }, [isSubmittable, close, label, expireDate])

    const intl = useIntl()

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/me/project/${id}/setting/share`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                {intl.formatMessage({id: 'project.detail.editing.url.create.title'})}
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalBody>
                    <FormGroup>
                        <Label>{intl.formatMessage({id: 'project.detail.editing.url.create.label'})}{' '}<RequiredBadge/></Label>
                        <Input type="text" required value={label} onChange={event => setLabel(event.target.value)}/>
                    </FormGroup>
                    <FormGroup>
                        <Label>{intl.formatMessage({id: 'project.detail.editing.url.create.expire.date'})}{' '}<RequiredBadge/></Label>
                        <Input type="date" required value={expireDate} onChange={event => setExpireDate(event.target.value)}/>
                    </FormGroup>
                </ModalBody>
                <ModalFooter>
                    <Button disabled={!isSubmittable} type="submit" color="primary">{intl.formatMessage({id: 'project.detail.editing.url.create.button'})}</Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Create