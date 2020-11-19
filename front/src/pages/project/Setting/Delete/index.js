import React from 'react'
import {Link} from 'react-router-dom'
import {Col, Row,} from 'reactstrap'

import Widget from '../../../../components/Widget'

const Delete = () => {
    return (
        <Row>
            <Col md="6">
                <Widget>
                    <h2 className="page-title">プロジェクトの削除</h2>
                    <Link to="/me/project/RMM10002/setting" className="btn btn-danger">プロジェクトを削除する</Link>
                </Widget>
            </Col>
        </Row>
    )
}

export default Delete