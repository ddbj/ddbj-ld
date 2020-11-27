import React, {useCallback, useMemo, useState} from 'react'
import {Link} from 'react-router-dom'
import {
    Button,
    Form,
    FormGroup,
    Input,
    InputGroup,
    InputGroupAddon,
    Label,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader
} from 'reactstrap'

import {ROLE_TYPE_EDITOR, ROLE_TYPE_VIEWER} from '../../../../hooks/project/setting'

const Result = ({user}) => {
    return user ? (
        <div className="pt-1">
            <Label>見つかったユーザ</Label>
            <div className="-mx-2 p-2 bg-white">
                <div className="mb-1">
                    <span className="text-font-bold mr-1">{user.name}</span>
                    <small>({user.id})</small>
                </div>
                <div>{user.email}</div>
            </div>
        </div>
    ) : (
        <div>
            見つかりませんでした
        </div>
    )
}

const Invite = ({match, history}) => {
    const {id} = match.params

    const [keyword, setKeyword] = useState('')
    const [user, setUser] = useState(undefined)
    const [type, setType] = useState(ROLE_TYPE_EDITOR)
    const [endDate, setEndDate] = useState('')

    const isSearchable = useMemo(() => {
        return !!keyword
    }, [keyword])

    const isInvitable = useMemo(() => {
        return !!user && !!type
    }, [type, user])

    const close = useCallback(() => {
        history.push(`/me/project/${id}/setting/role`)
    }, [history, id])

    const searchHandler = useCallback(event => {
        event.preventDefault()
        event.stopPropagation()

        if (!isSearchable) return

        if (!keyword.match(/test/)) {
            setUser(null)
        }

        setUser({
            id: keyword,
            email: `${keyword}@example.com`,
            name: 'テストユーザー'
        })
    }, [isSearchable, keyword])

    const inviteHandler = useCallback(event => {
        event.preventDefault()
        alert('招待しました')
        setUser(undefined)
        setEndDate('')
        setType(ROLE_TYPE_EDITOR)
    }, [])

    return (
        <Modal isOpen={true} toggle={close}>
            <ModalHeader>
                <Link to={`/me/project/${id}/setting/member`} className="p-2 mr-2 text-secondary">
                    <i className="fa fa-remove"/>
                </Link>
                共同編集者を招待
            </ModalHeader>
            <Form onSubmit={inviteHandler}>
                <ModalBody>
                    <Form onSubmit={searchHandler} className="mb-n4">
                        <FormGroup>
                            <Label>招待するユーザーのID <small>(プロトタイプ用: testで検索成功する)</small></Label>
                            <InputGroup>
                                <Input value={keyword} placeholder="ID"
                                       onChange={event => setKeyword(event.target.value)}/>
                                <InputGroupAddon addonType="append">
                                    <Button disabled={!isSearchable} color="primary">検索</Button>
                                </InputGroupAddon>
                            </InputGroup>
                            {user !== undefined ? (
                                <Result user={user}/>
                            ) : null}
                        </FormGroup>
                    </Form>
                    <input type="hidden" name="user" value={user ? user.id : null}/>
                    <FormGroup>
                        <Label>権限</Label>
                        <Input disabled type="select" value={type} onChange={event => setType(event.target.value)}>
                            <option value={ROLE_TYPE_EDITOR}>共同編集者</option>
                        </Input>
                    </FormGroup>
                    {type === ROLE_TYPE_VIEWER ? (
                        <FormGroup>
                            <Label>終了日 (<span>無期限の場合は未記入</span>)</Label>
                            <Input type="date" value={endDate} onChange={event => setEndDate(event.target.value)}/>
                        </FormGroup>
                    ) : null}
                </ModalBody>
                <ModalFooter>
                    <Button disabled={!isInvitable} color="primary">招待する</Button>
                </ModalFooter>
            </Form>
        </Modal>
    )
}

export default Invite