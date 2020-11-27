import React, {useCallback} from 'react'
import {Link, Redirect} from 'react-router-dom'
import {Button, Form, Modal, ModalFooter, ModalHeader} from 'reactstrap'

import {useTemporaryMember} from '../../../../hooks/project/setting'

import {useIntl} from "react-intl";
import {useDispatch} from "react-redux";
import {deleteAccessGrant} from "../../../../actions/project";

const Delete = ({match, history}) => {
    const {id, token} = match.params
    const member = useTemporaryMember(id, token)

    const close = useCallback(() => history.push(`/me/project/${id}/setting/share`), [history, id])

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
        dispatch(deleteAccessGrant(id, token))
        close()
    }, [close])

    const intl = useIntl()

    if (!member) return <Redirect to={`/me/project/${id}/setting/share`}/>

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/me/project/${id}/setting/share`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                {intl.formatMessage({id: 'project.detail.editing.url.delete.title'})}
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalFooter>
                    <Button type="submit" color="danger">{intl.formatMessage({id: 'project.detail.editing.url.delete.button'})}{' '}</Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Delete