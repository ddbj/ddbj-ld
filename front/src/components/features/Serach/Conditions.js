import { useIntl } from 'react-intl';
import { format, startOfDay, endOfDay } from 'date-fns';
import { DataSearch, SelectedFilters, ToggleButton, SingleList, DateRange } from '@appbaseio/reactivesearch';

const REACTIVE_SERACH_PROPS_REACT = Object.freeze({
  'and': ['query', 'isPartOf', 'type', 'organism', 'datePublished']
});

const dateRangeCustomQuery = (value, props) => {
  console.log(value)
  if (!value) return undefined

  const range = {}
  range[props.dataField] = {}
  if (value?.start) range[props.dataField].gte = format(startOfDay(new Date(value.start)), 'yyyy-MM-dd\'T\'HH:mm:ssxxx')
  if (value?.end) range[props.dataField].lte = format(endOfDay(new Date(value.end)), 'yyyy-MM-dd\'T\'HH:mm:ssxxx')

  const query = { bool: { must: [{ bool: { must:[{ range }] } }] } }
  return { query }
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
        filterLabel={intl.formatMessage({ id: 'search.search_keyword' })}
        fieldWeights={[1, 3, 3, 3, 3, 3, 3]}
        autosuggest showFilter URLParams
        queryFormat="and"
        fuzziness="AUTO"
        debounce={100}
        react={REACTIVE_SERACH_PROPS_REACT} />
      <ToggleButton
        componentId="isPartOf" dataField="isPartOf"
        title={intl.formatMessage({ id: 'search.select_part_of' })}
        filterLabel={intl.formatMessage({ id: 'search.select_part_of' })}
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
        title={intl.formatMessage({ id: 'search.select_type' })}
        filterLabel={intl.formatMessage({ id: 'search.select_type' })}
        URLParams react={REACTIVE_SERACH_PROPS_REACT} />
      <SingleList
        componentId="organism" dataField="organism.name.keyword"
        title={intl.formatMessage({ id: 'search.select_organism' })}
        filterLabel={intl.formatMessage({ id: 'search.select_organism' })}
        URLParams react={REACTIVE_SERACH_PROPS_REACT} />
      <DateRange
        componentId="datePublished" dataField="datePublished"
        title={intl.formatMessage({ id: 'search.select_published_date' })}
        filterLabel={intl.formatMessage({ id: 'search.select_published_date' })}
        URLParams
        customQuery={dateRangeCustomQuery}
        react={REACTIVE_SERACH_PROPS_REACT}
      />
      <SelectedFilters />
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
    </div>
  );
};
