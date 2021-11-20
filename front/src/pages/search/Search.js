import React from 'react'
import { ReactiveBase, ReactiveList } from '@appbaseio/reactivesearch'
import Config from '../../config'
import ResultByTable from "./ResultByTable"
import SearchConditions from "./SearchConditions"
import { Row, Col } from 'react-bootstrap'
import Loader from "../../components/Loader";

const { ResultListWrapper } = ReactiveList

const Search = () => {
    const searchStyle = { width: '100%'}
    // FIXME showResultStats, renderResultStatsを使い件数表示を調整する https://opensource.appbase.io/reactive-manual/result-components/reactivelist.html

    return (
        <ReactiveBase
            app="jga-*,sra-*,bioproject,biosample"
            url={Config.elasticsearchUrl}
            style={searchStyle}
        >
            <h1 style={{width: "100%"}}>DDBJ Search</h1>
            <Row>
                <Col lg={3}>
                    <SearchConditions />
                </Col>
                <Col lg={9}>
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
                        {({data, loading}) => {

                            if(loading) {
                                return <Loader size={400}/>
                            }

                            return (
                                <ResultListWrapper style={{ width: "100%"}}>
                                    {/* スクロールバーはユーザーのリクエストにより使用しない */}
                                    {
                                        data.map(item => (
                                            <ResultByTable item={item} key={item._id}/>
                                        ))
                                    }
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
