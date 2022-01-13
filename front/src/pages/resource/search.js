import { useMemo } from 'react';
import Head from 'next/head';
import { FormattedDate, FormattedMessage, FormattedTime, useIntl } from 'react-intl';
import { Badge, Card, CardBody, CardSubtitle } from 'reactstrap';
import {
  ReactiveBase,
  ReactiveList,
  DataSearch,
  SelectedFilters,
  ToggleButton,
  SingleList,
  DateRange,
  ResultList
} from '@appbaseio/reactivesearch';

import { elasticsearchUrl, apiBaseUrl } from '../../config';

import Page, { PageTitle } from '../../components/Page';
import Loading from '../../components/Loading';
import { useTitle } from '../../hooks/page';

const REACTIVE_SERACH_PROPS_REACT = Object.freeze({
  'and': ['query', 'isPartOf', 'type', 'organism', 'datePublished']
});

function ConditionTitle (props) {
  return <div className="fw-bold text-secondary" {...props} />;
}

function Conditions () {
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

function ResultByTable  ({ item }) {
  const title = useMemo(() => item.title || item.description || item.name, [item]);
  const detailUrl = useMemo(() => `${apiBaseUrl}/resource/${item.type}/${item.identifier}`, []);

  const refsCount = item.dbXrefs.length;

  const groups = useMemo(() =>
    Object.entries(
      item.dbXrefs
        .reduce(
          (result, dbXref) =>
            ({
              ...result,
              [dbXref.type]: (result[dbXref.type] || 0) + 1
            })
          , {}
        )
    ).map(([type, count]) => ({ type, count }))
  , [item]);

  return (
    <ResultList className="w-full bg-transparent d-block w-full" href={detailUrl}>
      <Card className="w-full">
        <CardBody className="d-flex flex-column gap-3">
          <div className="d-flex justify-content-between fs-6">
            <span>{item.identifier}</span>
            <span>{item.type}</span>
          </div>
          <CardSubtitle className="verflow-hidden text-truncate d-block fs-4 fw-bold">
            {title}
          </CardSubtitle>
          <div className="d-flex justify-content-between align-items-end fs-6">
            <div className="d-flex justify-content-start gap-2">
              <div>
                <FormattedMessage id="etnry.related_entry.message" values={{ refsCount }} />
              </div>
              <div className="d-flex gap-2">
                {groups.map(group =>
                  <Badge className="bg-light text-black" key={group.type}>{group.type} : {group.count}</Badge>
                )}
              </div>
            </div>
            {item.datePublished && (
              <div className="d-flex gap-2">
                <FormattedMessage id="entry.published_at" />
                <time dateTime={item.datePublished} className="d-flex gap-2 text-nowrap">
                  <span><FormattedDate value={item.datePublished} /></span>
                  <span><FormattedTime value={item.datePublished} /></span>
                </time>
              </div>
            )}
          </div>
        </CardBody>
      </Card>
    </ResultList>
  );
}

function Result () {
  return (
    <ReactiveList
      componentId="list"
      dataField="identifier,isPartOf,type,organism.name,datePublished"
      size={10}
      pagination
      react={{
        'and': ['query', 'title', 'description', 'name', 'isPartOf', 'type', 'organism', 'datePublished']
      }}>
      {({ data, loading }) => loading ? <Loading /> : (
        <ReactiveList.ResultListWrapper className="w-full">
          {
            /* スクロールバーはユーザーのリクエストにより使用しない */
            data.map(item => <ResultByTable key={item._id} item={item}/>)
          }
        </ReactiveList.ResultListWrapper>
      )}
    </ReactiveList>
  );
}

export default function Search () {
  const intl = useIntl();

  const title = useTitle(
    intl.formatMessage({ id: 'resource.search' })
  );

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <PageTitle>
          <FormattedMessage id="resource.search" />
        </PageTitle>
        <ReactiveBase
          className="w-full"
          app="jga-*,sra-*,bioproject,biosample"
          url={elasticsearchUrl}>
          <div className="d-flex gap-4">
            <div style={{ width: '20rem' }}>
              <Conditions />
            </div>
            <div className="flex-grow-1">
              <Result />
            </div>
          </div>
        </ReactiveBase>
      </Page>
    </>
  );
}
