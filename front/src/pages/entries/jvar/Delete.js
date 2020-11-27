<<<<<<< HEAD
import React from 'react'
import { Link } from 'react-router-dom'
import {
    Button,
    Form,
    Modal,
    ModalFooter,
    ModalHeader
} from 'reactstrap'
import { useDeleteEntry } from "../../../hooks/entries/jvar"

const Delete = ({ match, history }) => {
    const { entryUUID } = match.params

    const {
        close,
        loading,
        deleteHandler,
    } = useDeleteEntry(history, entryUUID)

    return (
        <Modal isOpen={true} toggle={loading ? null : close}>
=======
import React, {useCallback, useState} from 'react'
import {Link} from 'react-router-dom'
import {Button, Form, Modal, ModalFooter, ModalHeader} from 'reactstrap'

import {useIntl} from "react-intl";
import {useDispatch} from "react-redux"
import { deleteEntry } from "../../../actions/entry"

const Delete = ({match, history}) => {
    const { uuid } = match.params

    const close = useCallback(() => history.push(`/entries/jvar`), [history])

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()

        dispatch(deleteEntry(history, uuid))
        close()
    }, [close])

    const intl = useIntl()

    return (
        <Modal isOpen={true} toggle={close}>
>>>>>>> 差分修正
            <ModalHeader>
                <Link to={`/entries/jvar`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
<<<<<<< HEAD
                Delete this entry?
            </ModalHeader>
            <ModalFooter>
                <Button
                    type="submit"
                    color="primary"
                    onClick={deleteHandler}
                    disabled={loading}
                >
                    {loading ? "Deleting..." : "Delete"}
                </Button>
            </ModalFooter>
=======
                {intl.formatMessage({id: 'entry.delete.title'})}
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalFooter>
                    <Button
                        type="submit"
                        color="primary"
                        onClick={submitHandler}
                    >
                        {intl.formatMessage({id: 'common.button.delete'})}
                    </Button>
                </ModalFooter>
            </Form>
>>>>>>> 差分修正
        </Modal>
    )
}

<<<<<<< HEAD
export default Delete
=======
export default Delete
>>>>>>> 差分修正
