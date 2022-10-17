import { useMemo } from 'react';
import { FormattedDate, FormattedMessage, FormattedTime } from 'react-intl';
import { Badge, Card, CardSubtitle } from 'reactstrap';
import { ReactiveList, ResultList } from '@appbaseio/reactivesearch';
import { API_BASE_URL } from '@/constants';
import Loading from '@/components/parts/Loading';

function ResultByTable  ({ item }) {
  const title = useMemo(() => item.title || item.description || item.name, [item]);
  const detailUrl = useMemo(() => `${API_BASE_URL}/resource/${item.type}/${item.identifier}`, [item.identifier, item.type]);

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
    <ResultList className="bg-transparent d-block" href={detailUrl}>
      <Card className="p-2 py-3">
        <div className="small mb-2">
          <span className="me-1">{item.identifier}</span>
          <span>{item.type}</span>
        </div>
        <CardSubtitle className="verflow-hidden d-block fs-4 lh-sm mb-3">{title}</CardSubtitle>
        <div className="small mb-2">
          <FormattedMessage id="etnry.related_entry.message" values={{ refsCount }} />
        </div>
        <div className="d-flex gap-3">
          <div className="d-flex gap-1 flex-grow-1">
            {groups.map(group =>
              <Badge className="bg-light text-black" key={group.type}>{group.type} : {group.count}</Badge>
            )}
          </div>
          {item.datePublished && (
            <div className="d-flex gap-2 small flex-shrink-0">
              <span>
                <FormattedMessage id="entry.published_at" />
              </span>
              <time dateTime={item.datePublished}>
                <span className="me-2"><FormattedDate value={item.datePublished} /></span>
                <span><FormattedTime value={item.datePublished} /></span>
              </time>
            </div>
          )}
        </div>

      </Card>
    </ResultList>
  );
}

export default function Result () {
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
        <ReactiveList.ResultListWrapper>
          {
            /* スクロールバーはユーザーのリクエストにより使用しない */
            data.map(item => <ResultByTable key={item._id} item={item}/>)
          }
        </ReactiveList.ResultListWrapper>
      )}
    </ReactiveList>
  );
}
