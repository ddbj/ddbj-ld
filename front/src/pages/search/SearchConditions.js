import React from 'react'
import { DataSearch, SelectedFilters, SingleList, DateRange } from '@appbaseio/reactivesearch'

const SearchConditions = () => {
    return (
        <span
            style={{ width: '100%'}}
        >
            <DataSearch
                componentId="query"
                dataField={["identifier", "title" ,"description", "name", "value", "properties.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.TAG", "properties.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.VALUE"]}
                title={<span style={{fontWeight: "bold", color: "#838282"}}>Search keyword</span>}
                fieldWeights={[1, 3, 3, 3, 3, 3, 3]}
                autosuggest={true}
                queryFormat="and"
                fuzziness={100}
                debounce={100}
                react={{
                    "and": ["query", "isPartOf", "type", "organism", "datePublished"]
                }}
                showFilter={true}
                filterLabel="Keyword filter"
                URLParams={true}
                style={{marginBottom: 15, width: "100%"}}
            />
            <SingleList
                componentId="type"
                dataField="type.keyword"
                title={<span style={{fontWeight: "bold", color: "#838282"}}>Select type</span>}
                URLParams={true}
                showSearch={false}
                react={{
                    "and": ["query", "isPartOf", "type", "organism", "datePublished"]
                }}
                style={{ width: '10%', minWidth: 150, marginBottom: 15}}
            />
            <SingleList
                componentId="organism"
                dataField="organism.name.keyword"
                title={<span style={{fontWeight: "bold", color: "#838282"}}>Select organism</span>}
                URLParams={true}
                showSearch={false}
                react={{
                    "and": ["query", "isPartOf", "type", "organism", "datePublished"]
                }}
                style={{ width: '10%', minWidth: 150, marginBottom: 15}}
            />
            <DateRange
                componentId="pub"
                dataField="datePublished"
                title={<span style={{fontWeight: "bold", color: "#838282"}}>Select datePublished</span>}
                queryFormat="date_time_no_millis"
                URLParams={true}
                react={{
                    "and": ["query", "isPartOf", "type", "organism", "datePublished"]
                }}
            />
            <SelectedFilters/>
        </span>
    )
}

export default SearchConditions