import { DataSearch, SelectedFilters, ToggleButton, SingleList, DateRange } from '@appbaseio/reactivesearch';
import { useIntl } from 'react-intl';

const REACTIVE_SERACH_PROPS_REACT = Object.freeze({
  'and': ['query', 'isPartOf', 'type', 'organism', 'datePublished']
});

function ConditionTitle ({ title }) {
  return <div className="mb-2">{title}</div>;
}

export default function Conditions () {
  const intl = useIntl();
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
        title={intl.formatMessage({ id: 'search.search_keyword' })}
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
        title={<ConditionTitle title={intl.formatMessage({ id: 'search.select_part_of' })} />}
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
        title={<ConditionTitle title={intl.formatMessage({ id: 'search.select_type' })} />}
        URLParams
        react={REACTIVE_SERACH_PROPS_REACT} />
      <SingleList
        componentId="organism" dataField="organism.name.keyword"
        title={<ConditionTitle title={intl.formatMessage({ id: 'search.select_organism' })} />}
        URLParams
        react={REACTIVE_SERACH_PROPS_REACT} />
      <DateRange
        componentId="datePublished" dataField="datePublished"
        title={<ConditionTitle title={intl.formatMessage({ id: 'search.select_published_date' })} />}
        queryFormat="date_time_no_millis"
        URLParams
        react={REACTIVE_SERACH_PROPS_REACT}
      />
      <SelectedFilters />
    </div>
  );
};
