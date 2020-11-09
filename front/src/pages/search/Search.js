import React from 'react'
import {
    Button,
    Col,
    Form,
    FormGroup,
    Input,
    InputGroup,
    InputGroupAddon,
    Label,
    Nav,
    NavItem,
    NavLink,
    Row,
    TabContent,
    TabPane,
} from 'reactstrap';
import Select from 'react-select';
import classnames from 'classnames';

import {SEARCH_OPERATION_OPTIONS, TAB_ADVANCED, TAB_SIMPLE, useSearchForm} from '../../hooks/search'

import Widget from '../../components/Widget'
import PublicProjectList from '../../components/ProjectList/PublicProjectList'
import Pagination from '../../components/Pagination'
import {useIntl} from "react-intl"

const Search = ({history, location}) => {
    const {
        tab,
        isSimple,
        isAdvanced,
        submitHandler,
        switchSimple,
        switchAdvanced,
        keyword, setKeyword,
        samples, setSamples, samplesOptions,
        instruments, setInstruments, instrumentsOptions,
        count, selectCount,
        pages, selectOffset,
        total, rows,
        isSubmittable,
        operation, setOperation,
    } = useSearchForm({history, location})

    const intl = useIntl()

    return (
        <>
            <h2 className="page-title">{intl.formatMessage({id: 'search.title'})}</h2>
            <p style={{fontSize: 13, marginBottom: 10}}>JVar is a part of DDBJ public service for metabolomics studies,
                experimental raw data, and their metadata. Its information is shared
                with MetaboLights at EMBL-EBI and Metabolomics Workbench in the USA.</p>
            <Form onSubmit={submitHandler}>
                <div className="clearfix">
                    <Nav tabs className={`float-left`}>
                        <NavItem>
                            <NavLink className={classnames({active: isSimple})} onClick={switchSimple}>
                                <span>{intl.formatMessage({id: 'search.search.keyword'})}</span>
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink className={classnames({active: isAdvanced})} onClick={switchAdvanced}>
                                <span>{intl.formatMessage({id: 'search.search.advanced'})}</span>
                            </NavLink>
                        </NavItem>
                    </Nav>
                </div>
                <TabContent className='mb-lg' activeTab={tab}>
                    <TabPane tabId={TAB_SIMPLE}>
                        <Row>
                            <Col md="9">
                                <FormGroup>
                                    <InputGroup>
                                        <Input placeholder={intl.formatMessage({id: 'search.input.keyword'})} value={keyword}
                                               onChange={(e) => setKeyword(e.target.value)}/>
                                        <InputGroupAddon addonType="append">
                                            <Button disabled={!isSubmittable} type="submit" color="primary">{intl.formatMessage({id: 'search.button'})}</Button>
                                        </InputGroupAddon>
                                    </InputGroup>
                                </FormGroup>
                            </Col>
                            <Col md="3">
                                <Select
                                    value={operation}
                                    onChange={setOperation}
                                    options={SEARCH_OPERATION_OPTIONS}/>
                            </Col>
                        </Row>
                    </TabPane>
                    <TabPane tabId={TAB_ADVANCED}>
                        <FormGroup row>
                            <Label md="3">{intl.formatMessage({id: 'search.search.keyword'})}</Label>
                            <Col md="9">
                                <Input placeholder={intl.formatMessage({id: 'search.input.keyword'})} value={keyword}
                                       onChange={(e) => setKeyword(e.target.value)}/>
                            </Col>
                        </FormGroup>
                        <FormGroup row>
                            <Label md="3">{intl.formatMessage({id: 'search.label.samples'})}</Label>
                            <Col md="9">
                                <Select
                                    isMulti
                                    placeholder={intl.formatMessage({id: 'search.input.samples'})}
                                    value={samples}
                                    onChange={setSamples}
                                    options={samplesOptions}
                                />
                            </Col>
                        </FormGroup>
                        <FormGroup row>
                            <Label md="3">{intl.formatMessage({id: 'search.label.instruments'})}</Label>
                            <Col md="9">
                                <Select
                                    isMulti
                                    placeholder={intl.formatMessage({id: 'search.input.instruments'})}
                                    value={instruments}
                                    options={instrumentsOptions}
                                    onChange={setInstruments}/>
                            </Col>
                        </FormGroup>
                        <Row className="justify-content-end">
                            <Col md="3">
                                <FormGroup>
                                    <Select
                                        value={operation}
                                        onChange={setOperation}
                                        options={SEARCH_OPERATION_OPTIONS}/>
                                </FormGroup>
                            </Col>
                            <Col md="3">
                                <Button disabled={!isSubmittable} type="submit" color="primary" block>{intl.formatMessage({id: 'search.button'})}</Button>
                            </Col>
                        </Row>
                    </TabPane>
                </TabContent>
            </Form>
            {rows && rows.length > 0 ? (
                <Widget>
                    <div className="text-right">
                        {intl.formatMessage({id: 'search.result.hit'})}: {total}{' '}{intl.formatMessage({id: 'search.result.results'})}
                    </div>
                    <PublicProjectList rows={rows}/>
                    <Pagination
                        pages={pages} count={count}
                        onOffsetChange={selectOffset} onCountChange={selectCount}/>
                </Widget>
            ) : null}
        </>
    )
}

export default Search
