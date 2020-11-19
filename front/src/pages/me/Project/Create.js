import React, {useState} from 'react'
import {Button, Modal, ModalFooter, ModalHeader} from 'reactstrap'
import {useDispatch} from 'react-redux'

import {createProject} from '../../../actions/project'
import Loading from 'react-loading'
import {useIntl} from "react-intl";

const Create = ({history, visible, setVisible}) => {
    const dispatch = useDispatch()

    const [isLoading, setLoading] = useState(false)

    const intl = useIntl()

    const submitHandler = () => {
        setLoading(true)
        dispatch(createProject(history, setLoading))
    }

    return (
        <Modal
            isOpen={visible}
            centered={true}
        >
            <ModalHeader>
                {isLoading
                    ? null
                    : <a className="fa fa-remove" onClick={() => setVisible(false)}/>
                }
                {intl.formatMessage({id: 'me.project.button.message'})}
            </ModalHeader>
            <ModalFooter>
                <Button
                    color="primary"
                    onClick={() => submitHandler()}
                    disabled={isLoading}
                >
                    {isLoading
                        ? <Loading type={'spin'} height={20} width={20}/>
                        : <span>{intl.formatMessage({id: 'me.project.button.create'})}</span>
                    }
                </Button>
            </ModalFooter>
        </Modal>
    )
}

export default Create