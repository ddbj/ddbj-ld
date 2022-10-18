import { ReactiveBase } from '@appbaseio/reactivesearch';
import { ELASTICSEARCH_URL } from '@/constants';

import Conditions from './Conditions';
import Result from './Result';
import { Container } from 'reactstrap';

export default function SearchResource () {
  return (
    <Container>
      <ReactiveBase
        app="jga-*,sra-*,bioproject,biosample"
        url={ELASTICSEARCH_URL}
        theme={{
          colors: {
            titleColor      : '#000000',
            textColor       : '#444950',
            primaryTextColor: '#ffffff',
            primaryColor    : '#ff8c00',
            alertColor      : '#fa383e',
          }
        }}>
        <div className="d-flex gap-4">
          <div className="py-4" style={{ width: '20rem' }}>
            <Conditions />
          </div>
          <div className="flex-grow-1 py-4">
            <Result />
          </div>
        </div>
      </ReactiveBase>
    </Container>
  );
}
