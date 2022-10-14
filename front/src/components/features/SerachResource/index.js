import { ReactiveBase } from '@appbaseio/reactivesearch';
import { ELASTICSEARCH_URL } from '@/constants';

import Conditions from './Conditions';
import Result from './Result';

export default function SearchResource () {
  return (
    <ReactiveBase
      app="jga-*,sra-*,bioproject,biosample"
      url={ELASTICSEARCH_URL}>
      <div className="d-flex gap-2">
        <div style={{ width: '20rem' }}>
          <Conditions />
        </div>
        <Result />
      </div>
    </ReactiveBase>
  );
}
