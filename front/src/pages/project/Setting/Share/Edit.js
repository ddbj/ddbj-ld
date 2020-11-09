import React, {useCallback, useMemo, useState} from 'react'
import {Link, Redirect} from 'react-router-dom'
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap'

import {useTemporaryMember} from '../../../../hooks/project/setting'

import {RequiredBadge} from '../../../../components/JVar/Form'
import {useIntl} from "react-intl";
import {useDispatch} from "react-redux";
import {updateAccessGrant} from "../../../../actions/project";

const Edit = ({match, history}) => {
    const {id, token} = match.params
    const member = useTemporaryMember(id, token)

    const [label, setLabel] = useState(member.label)
    const [expireDate, setExpireDate] = useState(member.expireDate)

    const close = useCallback(() => history.push(`/me/project/${id}/setting/share`), [history, id])

    const isSubmittable = useMemo(() => {
        return !!label && !!expireDate
    }, [label, expireDate])

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return
        dispatch(updateAccessGrant(id, token, label, expireDate))
        close()
    }, [isSubmittable, close, label, expireDate])

    const intl = useIntl()

    if (!member) return <Redirect to={`/me/project/${id}/setting/share`}/>

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/me/project/${id}/setting/share`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                {intl.formatMessage({id: 'project.detail.editing.url.edit.title'})}
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalBody>
                    <FormGroup>
                        <Label>{intl.formatMessage({id: 'project.detail.editing.url.edit.label'})}{' '}<RequiredBadge/></Label>
                        <Input type="text" required value={label} onChange={event => setLabel(event.target.value)}/>
                    </FormGroup>
                    <FormGroup>
                        <Label>{intl.formatMessage({id: 'project.detail.editing.url.edit.expire.date'})}{' '}<RequiredBadge/></Label>
                        <Input type="date" value={expireDate} onChange={event => setExpireDate(event.target.value)}/>
                    </FormGroup>
                </ModalBody>
                <ModalFooter>
                    <Button disabled={!isSubmittable} type="submit" color="primary">{intl.formatMessage({id: 'project.detail.editing.url.edit.button'})}{' '}</Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Edit