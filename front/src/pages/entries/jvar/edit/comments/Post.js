import React from 'react'
import { Link } from 'react-router-dom'
import {
    Button,
    Form,
    Input,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader} from 'reactstrap'
import {useComment} from "../../../../../hooks/entries/jvar";

const Post = ({history, match}) => {
    const { entryUUID } = match.params
    const {
        comment,
        setComment,
        isLoading,
        close,
        postIsSubmittable,
        postHandler
    } = useComment(history, entryUUID)

    return (
        <Modal isOpen={true} toggle={isLoading ? null : close}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}/comments`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Comment
            </ModalHeader>
            <Form onSubmit={postHandler}>
                <ModalBody>
                    <Input type="textarea" value={comment} onChange={event => setComment(event.target.value)}/>
                </ModalBody>
                <ModalFooter>
                    <Button disabled={isLoading || !postIsSubmittable} type="submit" color="primary">{isLoading ? "Posting..." : "Post"}</Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Post