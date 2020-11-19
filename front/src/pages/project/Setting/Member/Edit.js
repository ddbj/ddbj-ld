import React, {useCallback, useState} from 'react'
import {Link, Redirect} from 'react-router-dom'
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap'

import {ROLE_TYPE_EDITOR, ROLE_TYPE_VIEWER, useMember} from '../../../../hooks/project/setting'

const Edit = ({match, history}) => {
    const {id, memberId} = match.params
    const member = useMember(id, memberId)

    const [startDate, setStartDate] = useState(member ? member.start_date : '')
    const [endDate, setEndDate] = useState(member ? member.end_date : '')
    const [role, setRole] = useState(member ? member.role : '')

    const close = useCallback(() => history.push(`/me/project/${id}/setting/member`), [history, id])

    const submitHandler = useCallback(event => {
        event.preventDefault()
        alert('保存しました')
        setStartDate('')
        setEndDate('')
        setRole(ROLE_TYPE_VIEWER)
    }, [])

    if (!member) return <Redirect to={`/me/project/${id}/setting/member`}/>

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/me/project/${id}/setting/member`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                関係者を編集
            </ModalHeader>
            <Form onSubmit={submitHandler}>
                <ModalBody>
                    <FormGroup>
                        <Label>ユーザ</Label>
                        <Input type="text" value={member.name} disabled/>
                    </FormGroup>
                    <FormGroup>
                        <Label>アカウントID</Label>
                        <Input type="text" value={member.id} disabled/>
                    </FormGroup>
                    <FormGroup>
                        <Label>権限</Label>
                        <Input type="select" value={role} onChange={event => setRole(event.target.value)} disabled>
                            <option value={ROLE_TYPE_VIEWER}>閲覧(共同閲覧者)</option>
                            <option value={ROLE_TYPE_EDITOR}>編集(共同編集者)</option>
                        </Input>
                    </FormGroup>
                    {role === ROLE_TYPE_VIEWER ? (
                        <>
                            <FormGroup>
                                <Label>追加日</Label>
                                <Input type="date" disabled value={startDate}
                                       onChange={event => setStartDate(event.target.value)}/>
                            </FormGroup>
                            <FormGroup>
                                <Label>終了日 (<span>無期限の場合は未記入</span>)</Label>
                                <Input type="date" value={endDate} onChange={event => setEndDate(event.target.value)}/>
                            </FormGroup>
                        </>
                    ) : null}
                </ModalBody>
                <ModalFooter>
                    <Button type="submit" color="primary">保存する</Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Edit