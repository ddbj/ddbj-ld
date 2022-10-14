import { DataSearch, SelectedFilters, ToggleButton, SingleList, DateRange } from '@appbaseio/reactivesearch';

const REACTIVE_SERACH_PROPS_REACT = Object.freeze({
  'and': ['query', 'isPartOf', 'type', 'organism', 'datePublished']
});

function ConditionTitle (props) {
  return <div className="fw-bold text-secondary" {...props} />;
}

export default function Conditions () {
  return (
    <div className="d-flex flex-column gap-4">
      <DataSearch
        componentId="query"
        dataField={[
          'identifier',
          'title',
          'description',
          'name',
          'value',
          'properties.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.TAG',
          'properties.STUDY_ATTRIBUTES.STUDY_ATTRIBUTE.VALUE',
          'properties.IDENTIFIERS.SECONDARY_ID',
        ]}
        title="Search keyword"
        fieldWeights={[1, 3, 3, 3, 3, 3, 3]}
        autosuggest showFilter URLParams
        queryFormat="and"
        fuzziness="AUTO"
        debounce={100}
        react={REACTIVE_SERACH_PROPS_REACT}
        filterLabel="Keyword filter" />
      {/* FIXME: dbXref検索用 */}
      {/*
        <DataSearch
           componentId="dbXref"
           dataField={[
               "identifier"
           ]}
           fieldWeights={[1, 3, 3, 3, 3, 3, 3]}
           autosuggest
           // queryFormat="and"
           fuzziness={0}
           debounce={100}
           react={{
               "and": ["dbXref", "query", "isPartOf", "type", "organism", "datePublished"]
           }}
           showFilter
           URLParams
           style={{display: "none"}}
       />
      */}
      <ToggleButton
        componentId="isPartOf" dataField="isPartOf"
        title={<ConditionTitle>Select partOf</ConditionTitle>}
        URLParams react={REACTIVE_SERACH_PROPS_REACT}
        data={[
          { 'label': 'JGA',   'value': 'jga' },
          { 'label': 'BIOPROJECT',   'value': 'bioproject' },
          { 'label': 'BIOSAMPLE',   'value': 'biosample' },
          { 'label': 'SRA',   'value': 'sra' },
        ]}
      />
      <SingleList
        componentId="type" dataField="type.keyword"
        title="Select type"
        URLParams
        react={REACTIVE_SERACH_PROPS_REACT} />
      <SingleList
        componentId="organism" dataField="organism.name.keyword"
        title="Select organism"
        URLParams
        react={REACTIVE_SERACH_PROPS_REACT} />
      <DateRange
        componentId="datePublished" dataField="datePublished"
        title="Select datePublished"
        queryFormat="date_time_no_millis"
        URLParams
        react={REACTIVE_SERACH_PROPS_REACT}
      />
      <SelectedFilters />
    </div>
  );
};
