import React from 'react'
import { ReactiveBase, ReactiveList } from '@appbaseio/reactivesearch'
import Config from '../../config'
import ResultByTable from "./ResultByTable"
import SearchConditions from "./SearchConditions"
import { Row, Col } from 'react-bootstrap'
<<<<<<< HEAD
=======
import { Scrollbars } from 'react-custom-scrollbars'
>>>>>>> 差分修正

const { ResultListWrapper } = ReactiveList

const Search = () => {
    const searchStyle = { width: '100%'}
    return (
        <ReactiveBase
<<<<<<< HEAD
            app="jga-*,bioproject"
=======
            app="jga-*"
>>>>>>> 差分修正
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
<<<<<<< HEAD
                        style={{ width: "95%" }}
=======
                        style={{ width: "95%"}}
>>>>>>> 差分修正
                    >
                        {({data}) => {
                            return (
                                <ResultListWrapper style={{ width: "100%"}}>
                                    {/* FIXME 他のページと高さを揃えるか、もしくは他のページをこちらにあわせるか */}
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

<<<<<<< HEAD
export default Search
=======
export default Search
>>>>>>> 差分修正
