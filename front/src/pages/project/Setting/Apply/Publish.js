import React, {useMemo} from 'react'
import {Link} from 'react-router-dom'
import {Button, Col, Modal, ModalFooter, ModalHeader, ModalBody, Row,} from 'reactstrap'

import Widget from '../../../../components/Widget'
import {useHasDraft, useIsApplying} from '../../../../hooks/project/status'
import {useIntl} from "react-intl";
import Loading from "react-loading";
import {useApply} from "../../../../hooks/project/setting";
import {useLocale} from "../../../../hooks/i18n";

const Publish = ({match}) => {
    const {id} = match.params

    const isApplying = useIsApplying(id)
    const intl = useIntl()
    const locale = useLocale()
    const hasDraft = useHasDraft(id)

    const applyStatus = useMemo(() => {
        if (isApplying) return intl.formatMessage({id: 'project.detail.editing.request.public.status.not'})
        return intl.formatMessage({id: 'project.detail.editing.request.public.status.done'})
    }, [isApplying, locale])

    const {
        visible,
        setVisible,
        isLoading,
        submitHandler
    } = useApply(id)

    return (
        <Row>
            <Col md="6">
                <Widget>
                    <h3 className="page-title">{intl.formatMessage({id: 'project.detail.editing.request.public.status.title'})} : {applyStatus}</h3>
                    <hr/>
                    <Button
                        color={"primary"}
                        onClick={() => setVisible(!visible)}
                        disabled={false === isApplying || false === hasDraft}
                    >
                        {intl.formatMessage({id: 'project.detail.editing.request.public.button'})}
                    </Button>
                    {false === hasDraft ? <p style={{marginTop: 10}}>{intl.formatMessage({id: 'project.detail.editing.request.message.not.exists.metadata'})}</p> : null}
                </Widget>
                <Modal
                    isOpen={visible}
                    centered={true}
                >
                    <ModalHeader>
                        {isLoading
                            ? null
                            : <a className="fa fa-remove" onClick={() => setVisible(false)}/>
                        }
                        {'　'}{intl.formatMessage({id: 'project.detail.editing.request.modal.title'})}
                    </ModalHeader>
                    <div
                        style={{backGroundColor: "#FFF", height: 100, width: '100%'}}
                    >
                        {'　　'}{intl.formatMessage({id: 'project.detail.editing.request.message'})}
                    </div>
                    <ModalFooter>
                        <Button
                            color={"primary"}
                            onClick={() => submitHandler()}
                            disabled={isLoading}
                        >
                            {isLoading
                                ? <Loading type={'spin'} height={20} width={20}/>
                                : intl.formatMessage({id: 'project.detail.editing.request.button'})
                            }
                        </Button>
                    </ModalFooter>
                </Modal>
            </Col>
        </Row>
    )
}

export default Publish