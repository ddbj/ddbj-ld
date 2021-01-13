import React from 'react'
import { Link } from 'react-router-dom'
import {
    Modal,
    ModalHeader,
    ModalFooter,
    Button
} from 'reactstrap'

const Update = ({ match, history }) => {
    const { entryUUID } = match.params

    // FIXME Applyボタン押下時のアクション

    return (
        <Modal isOpen={true}>
            <ModalHeader>
                <Link to={`/entries/jvar/${entryUUID}`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                Request to update?
            </ModalHeader>
            <ModalFooter>
                <Button
                    type="submit"
                    color="primary"
                    onClick={null}
                >
                    Apply
                </Button>
            </ModalFooter>
        </Modal>
    )
}

export default Update