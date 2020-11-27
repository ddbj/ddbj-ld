import React from 'react'
<<<<<<< HEAD
import {
    DataSearch,
    ToggleButton,
    SelectedFilters,
    SingleList,
    DateRange
} from '@appbaseio/reactivesearch'
=======
import { DataSearch, SelectedFilters, SingleList, DateRange } from '@appbaseio/reactivesearch'
>>>>>>> 差分修正

const SearchConditions = () => {
    return (
        <span
            style={{ width: '100%'}}
        >
            <DataSearch
                componentId="query"
<<<<<<< HEAD
                dataField={["identifier", "title" ,"description", "name", "value", "properties.STUDY.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.TAG", "properties.STUDY.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.VALUE", "properties.STUDY.IDENTIFIERS.SECONDARY_ID"]}
=======
                dataField={["identifier", "title" ,"description", "name", "value", "properties.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.TAG", "properties.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.VALUE"]}
>>>>>>> 差分修正
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
<<<<<<< HEAD
            <ToggleButton
                componentId="isPartOf"
                dataField="isPartOf"
                title={<span style={{fontWeight: "bold"}}>Select partOf</span>}
                data={[
                    {"label": "JGA",   "value": "jga"},
                    {"label": "BioProject",   "value": "bioproject"}
                ]}
                URLParams={true}
                react={{
                    "and": ["query", "isPartOf", "type", "organism", "datePublished"]
                }}
                style={{ marginBottom: 15 }}
            />
=======
>>>>>>> 差分修正
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
<<<<<<< HEAD
                style={{ width: '10%', minWidth: 280, marginBottom: 15 }}
=======
                style={{ width: '10%', minWidth: 150, marginBottom: 15}}
>>>>>>> 差分修正
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
<<<<<<< HEAD
                style={{ marginBottom: 15 }}
=======
>>>>>>> 差分修正
            />
            <SelectedFilters/>
        </span>
    )
}

<<<<<<< HEAD
export default SearchConditions
=======
export default SearchConditions
>>>>>>> 差分修正
