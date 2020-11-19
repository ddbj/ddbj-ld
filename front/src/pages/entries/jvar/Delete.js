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
            <ModalHeader>
                <Link to={`/entries/jvar`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
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
        </Modal>
    )
}

export default Delete