import React from 'react'
import {Link} from 'react-router-dom'
import {Col, Row,} from 'reactstrap'

import Widget from '../../../../components/Widget/Widget'
import {useIntl} from "react-intl";
import {NavLink} from "../../../../components/Navigation";
import { Button } from "reactstrap";

const Cancel = ({match}) => {
    const {id} = match.params
    const intl = useIntl()

    return (
        <Row>
            <Col md="6">
                <Widget>
                    <h2 className="page-title">{intl.formatMessage({id: 'project.detail.editing.tab.editing.cancel.title'})}</h2>
                    <p>{intl.formatMessage({id: 'project.detail.editing.tab.editing.cancel.description'})}</p>
                     <Button color="warning">{intl.formatMessage({id: 'project.detail.editing.tab.editing.cancel.button'})}</Button>
                </Widget>
            </Col>
        </Row>
    )
}

export default Cancel