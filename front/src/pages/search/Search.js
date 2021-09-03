import React from 'react'
import { ReactiveBase, ReactiveList } from '@appbaseio/reactivesearch'
import Config from '../../config'
import ResultByTable from "./ResultByTable"
import SearchConditions from "./SearchConditions"
import { Row, Col } from 'react-bootstrap'

const { ResultListWrapper } = ReactiveList

// FIXME loaderを試し、ローディング処理を実装する
const Search = () => {
    const searchStyle = { width: '100%'}
    return (
        <ReactiveBase
            app="jga-*,dra-*,bioproject,biosample"
            url={Config.elasticsearchUrl}
            style={searchStyle}
        >
            <h1 style={{width: "100%"}}>DDBJ Search</h1>
            <Row>
                <Col lg={2}>
                    <SearchConditions />
                </Col>
                <Col lg={10}>
                    <ReactiveList
                        componentId="list"
                        dataField="identifier,isPartOf,type,organism.name,datePublished"
                        size={10}
                        pagination={true}
                        react={{
                            "and": ["query", "title", "description", "name", "isPartOf", "type", "organism", "datePublished"]
                        }}
                        style={{ width: "95%" }}
                    >
                        {({data}) => {
                            return (
                                <ResultListWrapper style={{ width: "100%"}}>
                                    {/*<Scrollbars style={{ width: "100%", height: "90vh" }}>*/}
                                    {
                                        data.map(item => (
                                            <ResultByTable item={item} key={item._id}/>
                                        ))
                                    }
                                    {/*</Scrollbars>*/}
                                </ResultListWrapper>
                            )
                        }}
                    </ReactiveList>
                </Col>
            </Row>
        </ReactiveBase>
    )
}

export default Search
