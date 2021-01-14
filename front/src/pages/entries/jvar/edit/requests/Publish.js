import React, {useCallback, useMemo, useState} from 'react'
import { Link } from 'react-router-dom'
import {
    Modal,
    ModalHeader,
    ModalFooter,
    Button, ModalBody, Input, Form
} from 'reactstrap'
import {useDispatch} from "react-redux";
import {postComment} from "../../../../../actions/entry";

const Publish = ({ match, history }) => {
    const { entryUUID } = match.params
    const [comment, setComment] = useState('')
    const [isLoading, setLoading] = useState(false)

    const close = useCallback(() => history.push(`/entries/jvar/${entryUUID}/comments`), [history])

    const isSubmittable = useMemo(() => {
        return !!comment
    }, [comment])

    const dispatch = useDispatch()

    const submitHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return

        setLoading(true)
        dispatch(postComment(history, entryUUID, comment, setLoading))
    }, [isSubmittable, close, comment])

    return (
        <Modal isOpen={true}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Request to public?
            </ModalHeader>
            <ModalBody>
                <Input
                    type="textarea"
                    placeholder="write a comment"
                    value={comment}
                    onChange={event => setComment(event.target.value)}
                />
            </ModalBody>
            <ModalFooter>
                <Button
                    type="submit"
                    color="primary"
                    onClick={null}
                >
                    Request
                </Button>
            </ModalFooter>
        </Modal>
    )
}

export default Publish