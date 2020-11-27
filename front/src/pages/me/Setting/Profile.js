import React, {useCallback} from 'react'
import {Button, Col, Form, FormGroup, Input, Label, Row} from 'reactstrap'

import Widget from '../../../components/Widget'

const defaultProfile = {
    id: 'test',
    name: 'テストユーザ',
    email: 'test@example.com'
}

const Profile = () => {

    const submitHandler = useCallback(event => event.prevetDefault(), [])

    return (
        <Row>
            <Col md="6">
                <Widget>
                    <h3 className="page-title">プロフィールの変更</h3>
                    <Form onSubmit={submitHandler}>
                        <FormGroup>
                            <Label>ID</Label>
                            <Input disabled type="text" value={defaultProfile.id}/>
                        </FormGroup>
                        <FormGroup>
                            <Label>名前</Label>
                            <Input disabled type="text" value={defaultProfile.name}/>
                        </FormGroup>
                        <FormGroup>
                            <Label>メールアドレス</Label>
                            <Input disabled type="text" value={defaultProfile.email}/>
                        </FormGroup>
                        <Button color="primary">保存する</Button>
                    </Form>
                </Widget>
            </Col>
        </Row>
    )
}

export default Profile