import React from 'react'
import { DataSearch, SelectedFilters,  ToggleButton, SingleDropdownList, SingleList, DateRange } from '@appbaseio/reactivesearch';

const SearchConditions = () => {
    return (
        <span
            style={{ width: '100%'}}
        >
            <DataSearch
                componentId="query"
                dataField={[
                    "identifier",
                    "title" ,
                    "description",
                    "name",
                    "value",
                    "properties.STUDY.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.TAG",
                    "properties.STUDY.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.VALUE",
                    "properties.*.IDENTIFIERS.SECONDARY_ID",
                ]}
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
            {/*<ToggleButton*/}
            {/*    componentId="isPartOf"*/}
            {/*    dataField="isPartOf"*/}
            {/*    title={<span style={{fontWeight: "bold"}}>Select partOf</span>}*/}
            {/*    data={[*/}
            {/*        {"label": "JGA",   "value": "jga"},*/}
            {/*        {"label": "DRA",   "value": "dra"}*/}
            {/*    ]}*/}
            {/*    URLParams={true}*/}
            {/*    react={{*/}
            {/*        "and": ["query", "isPartOf", "type", "organism", "pub"]*/}
            {/*    }}*/}
            {/*    style={{marginBottom: 15}}*/}
            {/*/>*/}
            <SingleList
                componentId="type"
                dataField="type.keyword"
                title={<span style={{fontWeight: "bold", color: "#838282"}}>Select type</span>}
                URLParams={true}
                showSearch={false}
                react={{
                    "and": ["query", "isPartOf", "type", "organism", "datePublished"]
                }}
                style={{ width: '10%', minWidth: 200, marginBottom: 15}}
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
                style={{ width: '10%', minWidth: 200, marginBottom: 15}}
            />
            <DateRange
                componentId="datePublished"
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