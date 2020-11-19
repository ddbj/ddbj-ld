import React, {useState} from 'react'
import {Col, Row} from 'reactstrap'

import {useProjects} from '../../../hooks/me'

import Widget from '../../../components/Widget'
import MyProjectList from '../../../components/ProjectList/MyProjectList'
import Pagination from '../../../components/Pagination'
import Create from "./Create";
import {Button} from "react-bootstrap";
import {useIntl} from "react-intl";

const Projects = ({location, history}) => {

    const [visible, setVisible] = useState(false)

    const {
        rows,
        pages,
        selectOffset,
        selectCount,
        count,
    } = useProjects({location, history})

    const intl = useIntl()

    return (
        <>
            <Row className="mb-4">
                <Col>
                    <h2>{intl.formatMessage({id: 'me.project.title'})}</h2>
                </Col>
                <Col>
                    <div className="d-flex justify-content-end">
                        <Button onClick={() => setVisible(!visible)}>{intl.formatMessage({id: 'me.project.button'})}</Button>
                    </div>
                </Col>
            </Row>
            <Widget>
                {rows ? (
                    <>
                        <MyProjectList rows={rows}/>
                        <Pagination
                            pages={pages} count={count}
                            onOffsetChange={selectOffset} onCountChange={selectCount}/>
                    </>
                ) : null}
            </Widget>
            <Create history={history} visible={visible} setVisible={setVisible}/>
        </>
    )
}

export default Projects