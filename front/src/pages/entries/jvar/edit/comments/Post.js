import React, {useCallback, useMemo, useState} from 'react'
import {Link} from 'react-router-dom'
import {Button, Form, Input, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap'

import {useDispatch} from "react-redux"
import { postComment } from "../../../../../actions/entry"

const Post = ({history, match}) => {
    const { uuid } = match.params
    const [comment, setComment] = useState('')
    const [isLoading, setLoading] = useState(false)

    const close = useCallback(() => history.push(`/entries/jvar/${uuid}/comments`), [history])

    const isSubmittable = useMemo(() => {
        return !!comment
    }, [comment])

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return

        setLoading(true)
        dispatch(postComment(history, uuid, comment, setLoading))
    }, [isSubmittable, close, comment])

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${uuid}/comments`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Comment
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalBody>
                    <Input type="textarea" value={comment} onChange={event => setComment(event.target.value)}/>
                </ModalBody>
                <ModalFooter>
                    <Button disabled={isLoading || !isSubmittable} type="submit" color="primary">{isLoading ? "Posting..." : "Post"}</Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Post