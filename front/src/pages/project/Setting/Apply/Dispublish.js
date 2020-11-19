import React, {useMemo} from 'react'
import {Link} from 'react-router-dom'
import {Col, Row,} from 'reactstrap'

import Widget from '../../../../components/Widget'
import {useIsDispublishApplying} from '../../../../hooks/project/status'

const Dispublish = ({match}) => {
    const {id} = match.params
    const isDispublishApplying = useIsDispublishApplying(id)

    const applyStatus = useMemo(() => {
        return isDispublishApplying ? '申請中' : '未申請'
    }, [isDispublishApplying])

    // FIXME 編集を中止、申請を差し戻す、公開申請あたりもここか？

    return (
        <Row>
            <Col md="6">
                <Widget>
                    <h2 className="page-title">非公開申請</h2>
                    {isDispublishApplying ? (
                        <Link to="/me/project/RMM10007/setting" className="btn btn-warning">非公開申請を取り下げる</Link>
                    ) : (
                        <Link to="/me/project/RMM10008/setting" className="btn btn-danger">非公開申請をする</Link>
                    )}
                    <hr/>
                    <h3 className="page-title">申請状況</h3>
                    <p className="text-right">{applyStatus}</p>
                </Widget>
            </Col>
        </Row>
    )
}

export default Dispublish